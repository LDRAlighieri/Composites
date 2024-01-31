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

package ru.ldralighieri.composites.carbon.processor.ext

import com.google.devtools.ksp.processing.Resolver
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.ksp.toTypeName
import ru.ldralighieri.composites.carbon.processor.model.ArgumentData
import ru.ldralighieri.composites.carbon.processor.model.PARSE_ARGUMENTS_ENTRY_PARAMETER_NAME
import ru.ldralighieri.composites.carbon.processor.model.PARSE_ARGUMENTS_HANDLE_PARAMETER_NAME
import ru.ldralighieri.composites.carbon.processor.model.booleanNullableTypeName
import ru.ldralighieri.composites.carbon.processor.model.booleanTypeName
import ru.ldralighieri.composites.carbon.processor.model.floatNullableTypeName
import ru.ldralighieri.composites.carbon.processor.model.floatTypeName
import ru.ldralighieri.composites.carbon.processor.model.intNullableTypeName
import ru.ldralighieri.composites.carbon.processor.model.intTypeName
import ru.ldralighieri.composites.carbon.processor.model.longNullableTypeName
import ru.ldralighieri.composites.carbon.processor.model.longTypeName
import ru.ldralighieri.composites.carbon.processor.model.stringNullableTypeName
import ru.ldralighieri.composites.carbon.processor.model.stringTypeName

private fun List<ArgumentData>.getArguments(transform: (ArgumentData) -> CharSequence): String {
    val notNullable: List<ArgumentData> = filterNot { it.isNullable }
    return if (notNullable.isEmpty()) ""
    else notNullable.joinToString(prefix = "/", separator = "/", transform = transform)
}

private fun List<ArgumentData>.getOptionalArguments(
    transform: (ArgumentData) -> CharSequence
): String {
    val nullable: List<ArgumentData> = filter { it.isNullable }
    return if (nullable.isEmpty()) ""
    else nullable.joinToString(prefix = "?", separator = "&", transform = transform)
}

internal fun List<ArgumentData>.getPathArguments(): String =
    getArguments { "{${it.name}}" }

internal fun List<ArgumentData>.getCreateArguments(): String =
    getArguments { "\$${it.name}" }

internal fun List<ArgumentData>.getOptionalPathArguments(): String =
    getOptionalArguments { "${it.name}={${it.name}}" }

internal fun List<ArgumentData>.getOptionalCreateArguments(): String =
    getOptionalArguments { "${it.name}=\$${it.name}" }

internal fun ArgumentData.toBackStackGetter(resolver: Resolver): CodeBlock = buildCodeBlock {
    if (resolver.isEnumType(type)) {
        add(
            format = "enumValueOf<%T>(${PARSE_ARGUMENTS_ENTRY_PARAMETER_NAME}" +
                ".arguments?.getString(\"$name\") ?: \"\")",
            type.makeNotNullable().toTypeName()
        )
    } else {
        add(
            format = "${PARSE_ARGUMENTS_ENTRY_PARAMETER_NAME}.arguments?.%L(\"$name\")",
            when (type.toTypeName()) {
                intTypeName, intNullableTypeName -> "getInt"
                longTypeName, longNullableTypeName -> "getLong"
                floatTypeName, floatNullableTypeName -> "getFloat"
                booleanTypeName, booleanNullableTypeName -> "getBoolean"
                stringTypeName, stringNullableTypeName -> "getString"
                else -> "getString"
            }
        )

        if (!isNullable) {
            add(
                format = " ?: %L",
                toDefaultValueLiteral(resolver)
            )
        }
    }
}

internal fun ArgumentData.toSavedStateHandleGetter(resolver: Resolver): CodeBlock = buildCodeBlock {
    add("${PARSE_ARGUMENTS_HANDLE_PARAMETER_NAME}[\"$name\"]")

    if (!isNullable) {
        add(
            format = " ?: %L",
            toDefaultValueLiteral(resolver)
        )
    }
}

internal fun ArgumentData.toDefaultValueLiteral(resolver: Resolver): Any? =
    defaultValue?.castValue(resolver)
        ?: run {
            val typeName: TypeName = type.toTypeName()
            when {
                typeName == intTypeName -> "0"
                typeName == longTypeName -> "0L"
                typeName == floatTypeName -> "0.0F"
                typeName == booleanTypeName -> "false"
                typeName == stringTypeName -> "\"\""
                resolver.isEnumType(type) -> "${type.getSimpleName()}.entries.first()"
                else -> null
            }
        }
