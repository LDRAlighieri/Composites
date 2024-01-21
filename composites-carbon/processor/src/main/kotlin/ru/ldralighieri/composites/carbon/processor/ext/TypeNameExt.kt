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

internal fun TypeName.cast(value: String): Any =
    String.format(
        format = "%s%s",
        when(this) {
            Int::class.asTypeName() -> value.toInt()
            Long::class.asTypeName() -> value.toLong()
            Float::class.asTypeName() -> value.toFloat()
            Boolean::class.asTypeName() -> value.toBoolean()
            String::class.asTypeName() -> "\"$value\""
            else -> "\"$value\""
        },
        when(this) {
            Long::class.asTypeName() -> "L"
            Float::class.asTypeName() -> "F"
            else -> ""
        }
    )
