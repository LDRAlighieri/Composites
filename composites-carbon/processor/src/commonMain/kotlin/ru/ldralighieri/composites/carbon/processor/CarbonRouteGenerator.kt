/*
 * Copyright 2023 Vladimir Raupov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.ldralighieri.composites.carbon.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LIST
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.joinToCode
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import com.squareup.kotlinpoet.withIndent
import ru.ldralighieri.composites.carbon.core.Destination
import ru.ldralighieri.composites.carbon.processor.ext.castValue
import ru.ldralighieri.composites.carbon.processor.ext.getCreateArguments
import ru.ldralighieri.composites.carbon.processor.ext.getOptionalCreateArguments
import ru.ldralighieri.composites.carbon.processor.ext.getOptionalPathArguments
import ru.ldralighieri.composites.carbon.processor.ext.getPathArguments
import ru.ldralighieri.composites.carbon.processor.ext.toBackStackGetter
import ru.ldralighieri.composites.carbon.processor.ext.toNavTypeClassName
import ru.ldralighieri.composites.carbon.processor.ext.toSavedStateHandleGetter
import ru.ldralighieri.composites.carbon.processor.model.ARGUMENTS_PROPERTY_NAME
import ru.ldralighieri.composites.carbon.processor.model.CREATE_FUNCTION_NAME
import ru.ldralighieri.composites.carbon.processor.model.CarbonRouteData
import ru.ldralighieri.composites.carbon.processor.model.DEEPLINK_PROPERTY_NAME
import ru.ldralighieri.composites.carbon.processor.model.PARSE_ARGUMENTS_ENTRY_PARAMETER_NAME
import ru.ldralighieri.composites.carbon.processor.model.PARSE_ARGUMENTS_FUNCTION_NAME
import ru.ldralighieri.composites.carbon.processor.model.PARSE_ARGUMENTS_HANDLE_PARAMETER_NAME
import ru.ldralighieri.composites.carbon.processor.model.ROUTE_PROPERTY_NAME
import ru.ldralighieri.composites.carbon.processor.model.namedNavArgumentClassName
import ru.ldralighieri.composites.carbon.processor.model.navArgumentMemberName
import ru.ldralighieri.composites.carbon.processor.model.navBackStackEntryClassName
import ru.ldralighieri.composites.carbon.processor.model.navDeepLinkClassName
import ru.ldralighieri.composites.carbon.processor.model.navDeepLinkMemberName
import ru.ldralighieri.composites.carbon.processor.model.savedStateHandleClassName

internal class CarbonRouteGenerator(private val codeGenerator: CodeGenerator) {

    fun generate(data: CarbonRouteData) {
        FileSpec
            .builder(
                packageName = data.packageName,
                fileName = data.fileName
            )
            .addType(
                TypeSpec.objectBuilder(data.fileName)
                    .addProperty(data.routeProperty())
                    .addProperty(data.argumentsProperty())
                    .addProperty(data.deepLinksProperty())
                    .addFunction(data.createFunction())
                    .apply {
                        if (data.arguments.isNotEmpty()) {
                            addFunction(data.parseArgumentsByStackEntryFunction())
                            addFunction(data.parseArgumentsBySavedStateHandleFunction())
                        }
                    }
                    .build()
            )
            .build()
            .writeTo(codeGenerator, aggregating = false)
    }
}

private fun CarbonRouteData.routeProperty(): PropertySpec =
    PropertySpec
        .builder(
            name = ROUTE_PROPERTY_NAME,
            type = String::class,
            KModifier.CONST
        )
        .initializer(
            buildCodeBlock {
                add(
                    format = "\"%L%L%L\"",
                    route,
                    arguments.getPathArguments(),
                    arguments.getOptionalPathArguments(),
                )
            }
        )
        .build()

private fun CarbonRouteData.argumentsProperty(): PropertySpec =
    PropertySpec
        .builder(
            name = ARGUMENTS_PROPERTY_NAME,
            type = LIST.parameterizedBy(namedNavArgumentClassName)
        )
        .initializer(
            buildCodeBlock {
                if (arguments.isEmpty()) add("emptyList()")
                else {
                    addStatement("listOf(")
                    withIndent {
                        add(
                            arguments.map { argument ->
                                buildCodeBlock {
                                    addStatement("%M(%S) {", navArgumentMemberName, argument.name)

                                    withIndent {
                                        addStatement(
                                            format = "type = %T",
                                            argument.type.toNavTypeClassName()
                                        )

                                        addStatement("nullable = %L", argument.isNullable)

                                        argument.defaultValue?.let {
                                            addStatement(
                                                format = "defaultValue = %L",
                                                it.castValue()
                                            )
                                        }
                                    }

                                    add("},")
                                }
                            }
                                .joinToCode(separator = "\n", suffix = "\n")
                        )
                    }
                    add(")")
                }
            }
        )
        .build()

private fun CarbonRouteData.deepLinksProperty(): PropertySpec =
    PropertySpec
        .builder(
            name = DEEPLINK_PROPERTY_NAME,
            type = LIST.parameterizedBy(navDeepLinkClassName)
        )
        .initializer(
            buildCodeBlock {
                if (deeplinkSchema.isEmpty()) add("emptyList()")
                else {
                    addStatement("listOf(")
                    withIndent {
                        add(
                            buildCodeBlock {
                                beginControlFlow("%M", navDeepLinkMemberName)

                                addStatement(
                                    format = "uriPattern = \"%L://\$%N\"",
                                    deeplinkSchema,
                                    ROUTE_PROPERTY_NAME
                                )

                                endControlFlow()
                            }
                        )
                    }
                    add(")")
                }
            }
        )
        .build()

private fun CarbonRouteData.createFunction() = FunSpec
    .builder(CREATE_FUNCTION_NAME)
    .addParameters(
        arguments
            .map { data ->
                ParameterSpec.builder(data.name, data.type.toTypeName())
                    .apply {
                        when {
                            data.isNullable -> defaultValue(format = "%L", null)
                            data.defaultValue != null ->
                                defaultValue(
                                    format = "%L",
                                    data.defaultValue.castValue()
                                )
                        }
                    }
                    .build()
            }
    )
    .returns(Destination.Compose::class)
    .addStatement(
        format = "return %T(\"%L%L%L\")",
        Destination.Compose::class,
        route,
        arguments.getCreateArguments(),
        arguments.getOptionalCreateArguments(),
    )
    .build()

private fun CarbonRouteData.parseArgumentsByStackEntryFunction() =
    FunSpec
        .builder(PARSE_ARGUMENTS_FUNCTION_NAME)
        .addParameter(
            ParameterSpec(
                name = PARSE_ARGUMENTS_ENTRY_PARAMETER_NAME,
                type = navBackStackEntryClassName
            )
        )
        .returns(className)
        .addCode(
            buildCodeBlock {
                add(format = "return %T(\n", className)
                withIndent {
                    add(
                        arguments.map { argument ->
                            buildCodeBlock {
                                addStatement(
                                    format = "%L = %L,",
                                    argument.name,
                                    argument.toBackStackGetter()
                                )
                            }
                        }
                            .joinToCode(separator = "")
                    )
                }
                add(")")
            }
        )
        .build()

private fun CarbonRouteData.parseArgumentsBySavedStateHandleFunction() =
    FunSpec
        .builder(PARSE_ARGUMENTS_FUNCTION_NAME)
        .addParameter(
            ParameterSpec(
                name = PARSE_ARGUMENTS_HANDLE_PARAMETER_NAME,
                type = savedStateHandleClassName
            )
        )
        .returns(className)
        .addCode(
            buildCodeBlock {
                add("return %T(\n", className)
                withIndent {
                    add(
                        arguments.map { argument ->
                            buildCodeBlock {
                                addStatement(
                                    format = "%L = %L,",
                                    argument.name,
                                    argument.toSavedStateHandleGetter()
                                )
                            }
                        }
                            .joinToCode(separator = "")
                    )
                }
                add(")")
            }
        )
        .build()
