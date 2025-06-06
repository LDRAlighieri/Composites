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

package ru.ldralighieri.composites.sample.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

@Immutable
internal data class AppDimensions(
    val shapeAppearanceSmall: Dp,
    val shapeAppearanceMedium: Dp,
    val shapeAppearanceLarge: Dp,
    val horizontalGuideline: Dp,
    val topGuideline: Dp,
    val bottomGuideline: Dp,
)

internal val LocalAppDimensions = staticCompositionLocalOf<AppDimensions> {
    error("No LocalAppDimensions specified")
}
