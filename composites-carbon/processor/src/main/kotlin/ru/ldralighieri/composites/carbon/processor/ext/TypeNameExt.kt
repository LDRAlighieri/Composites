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

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import ru.ldralighieri.composites.carbon.processor.PARSE_ARGUMENTS_ENTRY_PARAMETER_NAME
import ru.ldralighieri.composites.carbon.processor.PARSE_ARGUMENTS_HANDLE_PARAMETER_NAME

private val navTypeIntClassName =
    ClassName("androidx.navigation.NavType.Companion", "IntType")

private val navTypeLongClassName =
    ClassName("androidx.navigation.NavType.Companion", "LongType")

private val navTypeFloatClassName =
    ClassName("androidx.navigation.NavType.Companion", "FloatType")

private val navTypeBooleanClassName =
    ClassName("androidx.navigation.NavType.Companion", "BoolType")

private val navTypeStringClassName =
    ClassName("androidx.navigation.NavType.Companion", "StringType")

internal fun TypeName.toClassName(): ClassName =
    when(this) {
        Int::class.asTypeName(),
        Int::class.asTypeName().copy(nullable = true) -> navTypeIntClassName

        Long::class.asTypeName(),
        Long::class.asTypeName().copy(nullable = true) -> navTypeLongClassName

        Float::class.asTypeName(),
        Float::class.asTypeName().copy(nullable = true) -> navTypeFloatClassName

        Boolean::class.asTypeName(),
        Boolean::class.asTypeName().copy(nullable = true) -> navTypeBooleanClassName

        String::class.asTypeName(),
        String::class.asTypeName().copy(nullable = true) -> navTypeStringClassName

        else -> navTypeStringClassName
    }

internal fun TypeName.toBackStackGetter(name: String): String =
    "${PARSE_ARGUMENTS_ENTRY_PARAMETER_NAME}.arguments?." +
            when(this) {
                Int::class.asTypeName() -> "getInt(\"$name\") ?: 0"
                Int::class.asTypeName().copy(nullable = true) -> "getInt(\"$name\")"

                Long::class.asTypeName() -> "getLong(\"$name\") ?: 0L"
                Long::class.asTypeName().copy(nullable = true) -> "getLong(\"$name\")"

                Float::class.asTypeName() -> "getFloat(\"$name\") ?: 0f"
                Float::class.asTypeName().copy(nullable = true) -> "getFloat(\"$name\")"

                Boolean::class.asTypeName() -> "getBoolean(\"$name\")"
                Boolean::class.asTypeName().copy(nullable = true) ->
                    "getBoolean(\"$name\") ?: false"

                String::class.asTypeName() -> "getString(\"$name\") ?: \"\""
                String::class.asTypeName().copy(nullable = true) -> "getString(\"$name\")"

                else -> "getString(\"$name\") ?: \"\""
            }

internal fun TypeName.toSavedStateHandleGetter(name: String): String =
    PARSE_ARGUMENTS_HANDLE_PARAMETER_NAME +
            when(this) {
                Int::class.asTypeName() -> "[\"$name\"] ?: 0"
                Int::class.asTypeName().copy(nullable = true) -> "[\"$name\"]"

                Long::class.asTypeName() -> "[\"$name\"] ?: 0L"
                Long::class.asTypeName().copy(nullable = true) -> "[\"$name\"]"

                Float::class.asTypeName() -> "[\"$name\"] ?: 0f"
                Float::class.asTypeName().copy(nullable = true) -> "[\"$name\"]"

                Boolean::class.asTypeName() -> "[\"$name\"] ?: false"
                Boolean::class.asTypeName().copy(nullable = true) -> "[\"$name\"]"

                String::class.asTypeName() -> "[\"$name\"] ?: \"\""
                String::class.asTypeName().copy(nullable = true) -> "[\"$name\"]"

                else -> "[\"$name\"] ?: \"\""
            }
