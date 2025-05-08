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

package ru.ldralighieri.composites.sample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.ldralighieri.composites.sample.ui.CompositesScreen
import ru.ldralighieri.composites.sample.ui.fiberglass.FiberglassColumnScreen
import ru.ldralighieri.composites.sample.ui.fiberglass.FiberglassGridScreen
import ru.ldralighieri.composites.sample.ui.fiberglass.FiberglassRootScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    AppAnimatedNavHost(
        navController = navController,
        startDestination = CompositesRoute.route,
        modifier = modifier,
    ) {
        composable(
            route = CompositesRoute.route,
            arguments = CompositesRoute.arguments,
            deepLinks = CompositesRoute.deepLinks,
        ) {
            CompositesScreen()
        }

        composable(
            route = CompositesFiberglassRoute.route,
            arguments = CompositesFiberglassRoute.arguments,
            deepLinks = CompositesFiberglassRoute.deepLinks,
        ) { navBackStackEntry ->
            FiberglassRootScreen(args = CompositesFiberglassRoute.parseArguments(navBackStackEntry))
        }

        composable(
            route = CompositesFiberglassExampleRoute.route,
            arguments = CompositesFiberglassExampleRoute.arguments,
            deepLinks = CompositesFiberglassExampleRoute.deepLinks,
        ) { navBackStackEntry ->
            val args: CompositesFiberglassExampleArgs =
                CompositesFiberglassExampleRoute.parseArguments(navBackStackEntry)
            when (args.type) {
                FiberglassType.Column -> FiberglassColumnScreen(args)
                FiberglassType.Grid -> FiberglassGridScreen(args)
            }
        }
    }
}
