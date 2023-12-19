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

package ru.ldralighieri.composites.sample.ui.app

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import ru.ldralighieri.composites.sample.navigation.AppNavHost
import ru.ldralighieri.composites.sample.theme.AppTheme

@Composable
fun ComponentActivity.CompositesApp(appState: CompositesAppState) {
    val currentThemeIsDark = isSystemInDarkTheme()
    val isDarkMode by rememberSaveable { mutableStateOf(currentThemeIsDark) }

    ChangeSystemBarsTheme(isDarkMode)

    ProvideDefaults {
        Column {
            Scaffold(
                containerColor = Color.Transparent,
                contentColor = AppTheme.colors.onBackground,
                contentWindowInsets = WindowInsets(0, 0, 0, 0)
            ) { innerPadding ->
                AppNavHost(
                    navController = appState.navController,
                    onBackClick = appState::onBackClick,
                    navigate = appState::navigate,
                    modifier = Modifier
                        .background(color = AppTheme.colors.background)
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun ComponentActivity.ChangeSystemBarsTheme(isDarkMode: Boolean) {
    val barColor = AppTheme.colors.background.toArgb()
    LaunchedEffect(isDarkMode) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT,
            ) { isDarkMode },

            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = barColor,
                darkScrim = barColor,
            ) { isDarkMode },
        )
    }
}

@Composable
private fun ProvideDefaults(content: @Composable () -> Unit) {
    AppTheme(dynamicColor = true) {
        @OptIn(ExperimentalFoundationApi::class)
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null,
            content = content
        )
    }
}
