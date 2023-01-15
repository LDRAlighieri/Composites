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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.ldralighieri.composites.sample.navigation.AppNavHost
import ru.ldralighieri.composites.sample.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompositesApp(appState: CompositesAppState) {
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
                        .padding(innerPadding)
                        .consumedWindowInsets(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun ProvideDefaults(content: @Composable () -> Unit) {
    AppTheme(dynamicColor = true) {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()

        DisposableEffect(systemUiController, useDarkIcons) {
            systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = useDarkIcons)
            onDispose {}
        }

        @OptIn(ExperimentalFoundationApi::class)
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null,
            content = content
        )
    }
}
