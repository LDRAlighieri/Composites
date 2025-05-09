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

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.ksp.toTypeName
import ru.ldralighieri.composites.carbon.processor.model.ArgumentData
import ru.ldralighieri.composites.carbon.processor.model.PARSE_ARGUMENTS_ENTRY_PARAMETER_NAME
import ru.ldralighieri.composites.carbon.processor.model.PARSE_ARGUMENTS_HANDLE_PARAMETER_NAME
import ru.ldralighieri.composites.carbon.processor.model.booleanTypeName
import ru.ldralighieri.composites.carbon.processor.model.floatTypeName
import ru.ldralighieri.composites.carbon.processor.model.intTypeName
import ru.ldralighieri.composites.carbon.processor.model.longTypeName
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

internal fun ArgumentData.toBackStackGetter(): CodeBlock = buildCodeBlock {
    add(
        format = "${PARSE_ARGUMENTS_ENTRY_PARAMETER_NAME}.savedStateHandle.get<%T>(\"$name\")",
        type.makeNotNullable().toTypeName(),
    )

    if (!isNullable) {
        add(
            format = " ?: %L",
            toDefaultValueLiteral()
        )
    }
}

internal fun ArgumentData.toSavedStateHandleGetter(): CodeBlock = buildCodeBlock {
    add(
        format = "${PARSE_ARGUMENTS_HANDLE_PARAMETER_NAME}.get<%T>(\"$name\")",
        type.makeNotNullable().toTypeName(),
    )

    if (!isNullable) {
        add(
            format = " ?: %L",
            toDefaultValueLiteral()
        )
    }
}

internal fun ArgumentData.toDefaultValueLiteral(): Any? =
    defaultValue?.castValue()
        ?: run {
            val typeName: TypeName = type.toTypeName()
            when (typeName) {
                intTypeName -> "0"
                longTypeName -> "0L"
                floatTypeName -> "0.0F"
                booleanTypeName -> "false"
                stringTypeName -> "\"\""
                else -> null
            }
        }
