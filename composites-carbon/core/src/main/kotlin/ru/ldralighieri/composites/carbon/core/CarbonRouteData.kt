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

package ru.ldralighieri.composites.carbon.core

import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName

data class CarbonRouteData(
    val route: String,
    val deeplinkSchema: String,
    val fileName: String,
    val className: ClassName,
    val packageName: String,
    val arguments: List<ArgumentData>,
)

data class ArgumentData(
    val name: String,
    val type: KSType,
    val isNullable: Boolean,
    val defaultValue: ArgumentDefaultValue? = null,
)

data class ArgumentDefaultValue(
    val value: String,
    val type: KSType,
)
