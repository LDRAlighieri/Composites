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

package ru.ldralighieri.composites.carbon.processor.model

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import ru.ldralighieri.composites.carbon.core.CarbonRoute

internal val carbonAnnotationTypeName: TypeName = CarbonRoute::class.java.asTypeName()

internal val intTypeName: TypeName = Int::class.asTypeName()
internal val intNullableTypeName: TypeName = intTypeName.copy(nullable = true)

internal val longTypeName: TypeName = Long::class.asTypeName()
internal val longNullableTypeName: TypeName = longTypeName.copy(nullable = true)

internal val floatTypeName: TypeName = Float::class.asTypeName()
internal val floatNullableTypeName: TypeName = floatTypeName.copy(nullable = true)

internal val booleanTypeName: TypeName = Boolean::class.asTypeName()
internal val booleanNullableTypeName: TypeName = booleanTypeName.copy(nullable = true)

internal val stringTypeName: TypeName = String::class.asTypeName()
internal val stringNullableTypeName: TypeName = stringTypeName.copy(nullable = true)

internal val validTypes: List<TypeName> = listOf(
    intTypeName, intNullableTypeName,
    longTypeName, longNullableTypeName,
    floatTypeName, floatNullableTypeName,
    booleanTypeName, booleanNullableTypeName,
    stringTypeName, stringNullableTypeName,
)
