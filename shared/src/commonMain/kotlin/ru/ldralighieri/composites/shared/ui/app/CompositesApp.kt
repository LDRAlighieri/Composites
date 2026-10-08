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

package ru.ldralighieri.composites.shared.ui.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.ldralighieri.composites.carbon.core.Destination
import ru.ldralighieri.composites.shared.navigation.AppNavHost
import ru.ldralighieri.composites.shared.navigation.LocalNavigator
import ru.ldralighieri.composites.shared.navigation.Navigator
import ru.ldralighieri.composites.shared.ui.theme.AppTheme

@Composable
public fun CompositesApp() {
    val navController: NavHostController = rememberNavController()
    val navigator = remember { Navigator() }

    LaunchedEffect(navigator, navController) {
        navigator.destinations.collect { event ->
            when (event) {
                is Navigator.Event.ToDestination -> {
                    when (val destination: Destination = event.destination) {
                        is Destination.Compose -> navController.navigate(destination.route)
                    }
                }

                Navigator.Event.Back -> {
                    // Repeated Back taps must not remove the last screen.
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    CompositionLocalProvider(LocalNavigator provides navigator) {
        AppTheme {
            Column {
                Scaffold(
                    containerColor = Color.Transparent,
                    contentColor = AppTheme.colors.onBackground,
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier
                            .background(color = AppTheme.colors.background)
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding),
                    )
                }
            }
        }
    }
}
