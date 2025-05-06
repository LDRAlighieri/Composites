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

import com.squareup.kotlinpoet.ClassName

internal val buildClassName = ClassName("android.os", "Build")
internal val namedNavArgumentClassName = ClassName("androidx.navigation", "NamedNavArgument")
internal val navDeepLinkClassName = ClassName("androidx.navigation", "NavDeepLink")
internal val navBackStackEntryClassName = ClassName("androidx.navigation", "NavBackStackEntry")
internal val savedStateHandleClassName = ClassName("androidx.lifecycle", "SavedStateHandle")

internal val navTypeIntClassName =
    ClassName("androidx.navigation.NavType.Companion", "IntType")

internal val navTypeLongClassName =
    ClassName("androidx.navigation.NavType.Companion", "LongType")

internal val navTypeFloatClassName =
    ClassName("androidx.navigation.NavType.Companion", "FloatType")

internal val navTypeBooleanClassName =
    ClassName("androidx.navigation.NavType.Companion", "BoolType")

internal val navTypeStringClassName =
    ClassName("androidx.navigation.NavType.Companion", "StringType")

internal val navTypeEnumClassName =
    ClassName("androidx.navigation.NavType", "EnumType")
