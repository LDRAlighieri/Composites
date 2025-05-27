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

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp

// Colors
private val DefaultLightColorScheme =
    lightColorScheme(
        primary = Purple40,
        onPrimary = Purple100,
        primaryContainer = Purple90,
        onPrimaryContainer = Purple10,
        secondary = Blue40,
        onSecondary = Blue100,
        secondaryContainer = Blue80,
        onSecondaryContainer = Blue10,
        tertiary = Braun40,
        onTertiary = Braun100,
        tertiaryContainer = Braun90,
        onTertiaryContainer = Braun10,
        background = Gray99,
        onBackground = Gray10,
        surface = Gray99,
        onSurface = Gray10,
        surfaceVariant = GrayVariant90,
        onSurfaceVariant = GrayVariant30,
        surfaceTint = Purple40,
        error = Red40,
        onError = Red100,
        errorContainer = Red90,
        onErrorContainer = Red10,
        outline = GrayVariant50,
        outlineVariant = GrayVariant80,
        scrim = Gray0,
    )

private val DefaultDarkColorScheme =
    darkColorScheme(
        primary = Purple80,
        onPrimary = Purple20,
        primaryContainer = Purple30,
        onPrimaryContainer = Purple90,
        secondary = Blue80,
        onSecondary = Blue20,
        secondaryContainer = Blue30,
        onSecondaryContainer = Blue90,
        tertiary = Braun80,
        onTertiary = Braun20,
        tertiaryContainer = Braun30,
        onTertiaryContainer = Braun90,
        background = Gray10,
        onBackground = Gray90,
        surface = Gray10,
        onSurface = Gray90,
        surfaceVariant = GrayVariant30,
        onSurfaceVariant = GrayVariant80,
        surfaceTint = Purple80,
        error = Red80,
        onError = Red20,
        errorContainer = Red30,
        onErrorContainer = Red90,
        outline = GrayVariant60,
        outlineVariant = GrayVariant30,
        scrim = Gray0,
    )

// Dimensions
private val DefaultAppDimensions =
    AppDimensions(
        shapeAppearanceSmall = 8.dp,
        shapeAppearanceMedium = 12.dp,
        shapeAppearanceLarge = 16.dp,
        horizontalGuideline = 16.dp,
        topGuideline = 12.dp,
        bottomGuideline = 24.dp,
    )

// Shapes
private val DefaultShapes =
    Shapes(
        small = RoundedCornerShape(DefaultAppDimensions.shapeAppearanceSmall),
        medium = RoundedCornerShape(DefaultAppDimensions.shapeAppearanceMedium),
        large = RoundedCornerShape(DefaultAppDimensions.shapeAppearanceLarge),
    )

// Theme
@Composable
internal fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val materialColorScheme = if (isDarkTheme) DefaultDarkColorScheme else DefaultLightColorScheme

    CompositionLocalProvider(
        LocalOverscrollFactory provides null,
        LocalAppDimensions provides DefaultAppDimensions,
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            typography = AppTypography,
            shapes = DefaultShapes,
            content = content,
        )
    }
}

internal object AppTheme {
    val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colorScheme

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.shapes

    val dimensions: AppDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalAppDimensions.current
}
