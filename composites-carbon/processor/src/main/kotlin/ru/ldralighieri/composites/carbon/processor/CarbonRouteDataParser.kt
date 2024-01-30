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

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import ru.ldralighieri.composites.carbon.core.ArgumentData
import ru.ldralighieri.composites.carbon.core.ArgumentDefaultValue
import ru.ldralighieri.composites.carbon.core.CarbonRouteData
import ru.ldralighieri.composites.carbon.core.DefaultValue
import ru.ldralighieri.composites.carbon.processor.ext.getSimpleName
import ru.ldralighieri.composites.carbon.processor.ext.isAcceptableType
import ru.ldralighieri.composites.carbon.processor.model.ROUTE_ARGUMENT_NAME
import ru.ldralighieri.composites.carbon.processor.model.ROUTE_DEEPLINK_SCHEMA_NAME
import ru.ldralighieri.composites.carbon.processor.model.ROUTE_FILE_NAME_POSTFIX
import ru.ldralighieri.composites.carbon.processor.model.carbonAnnotationTypeName
import java.util.Locale

internal class CarbonRouteDataParser {

    fun parse(resolver: Resolver, symbol: KSClassDeclaration): Result {

        val annotation: KSAnnotation =
            symbol
                .annotations
                .toList()
                .firstOrNull { it.annotationType.toTypeName() == carbonAnnotationTypeName }
                ?: return Result.Failure(
                    message = "Error to process. There is no $carbonAnnotationTypeName annotation",
                    symbol = symbol
                )

        val route: String =
            annotation
                .arguments
                .filter { it.name?.getShortName() == ROUTE_ARGUMENT_NAME }
                .map {
                    val value = it.value.toString()
                    if (value.endsWith("/")) value.dropLast(1) else value
                }
                .firstOrNull()
                ?: return Result.Failure(
                    message = "Error to process. There is no argument named $ROUTE_ARGUMENT_NAME",
                    symbol = symbol
                )

        val deeplinkSchema: String =
            annotation
                .arguments
                .filter { it.name?.getShortName() == ROUTE_DEEPLINK_SCHEMA_NAME }
                .map { it.value.toString() }
                .firstOrNull()
                ?: return Result.Failure(
                    message = "Error to process. There is no argument named $ROUTE_ARGUMENT_NAME",
                    symbol = symbol
                )

        val packageName: String = symbol.packageName.asString()
        val fileName: String = route.getFileName()
        val className: ClassName = symbol.toClassName()

        val arguments: List<ArgumentData> =
            try { symbol.getArguments(resolver) }
            catch (e: IllegalArgumentException) { return Result.Failure(e.message.orEmpty()) }

        val data = CarbonRouteData(
            route = route,
            deeplinkSchema = deeplinkSchema,
            packageName = packageName,
            fileName = fileName,
            className = className,
            arguments = arguments
        )

        return Result.Success(data)
    }

    private fun String.getFileName(): String =
        this
            .split("[-,/]".toRegex())
            .joinToString(separator = "", postfix = ROUTE_FILE_NAME_POSTFIX) { string ->
                string.replaceFirstChar { char ->
                    with(char) {
                        if (isLowerCase()) titlecase(Locale.getDefault())
                        else toString()
                    }
                }
            }

    @OptIn(KspExperimental::class)
    @Throws(IllegalStateException::class)
    private fun KSClassDeclaration.getArguments(resolver: Resolver): List<ArgumentData> =
        this
            .primaryConstructor
            ?.parameters
            .orEmpty()
            .map {
                val type: KSType = it.type.resolve()
                if (!type.isAcceptableType(resolver))
                    error("${type.getSimpleName()} is not a valid argument type")

                val name: String = it.name?.getShortName().orEmpty()
                val isNullable: Boolean = type.isMarkedNullable

                val defaultValue: ArgumentDefaultValue? = it
                    .getAnnotationsByType(DefaultValue::class)
                    .firstOrNull()
                    ?.let { defaultValue ->
                        ArgumentDefaultValue(
                            value = defaultValue.value,
                            type = type,
                        )
                    }

                ArgumentData(name, type, isNullable, defaultValue)
            }

    sealed interface Result {
        data class Success(val data: CarbonRouteData) : Result
        data class Failure(val message: String, val symbol: KSNode? = null) : Result
    }
}
