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

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

fun NavGraphBuilder.appComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),

    enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        slideInHorizontally(
            initialOffsetX = { 300 },
            animationSpec = tween(durationMillis = 300)
        ) +
            fadeIn(animationSpec = tween(durationMillis = 300))
    },

    exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        slideOutHorizontally(
            targetOffsetX = { -300 },
            animationSpec = tween(durationMillis = 300)
        ) +
            fadeOut(animationSpec = tween(durationMillis = 300))
    },

    popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        slideInHorizontally(
            initialOffsetX = { -300 },
            animationSpec = tween(durationMillis = 300)
        ) +
            fadeIn(animationSpec = tween(durationMillis = 300))
    },

    popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        slideOutHorizontally(
            targetOffsetX = { 300 },
            animationSpec = tween(durationMillis = 300)
        ) +
            fadeOut(animationSpec = tween(durationMillis = 300))
    },

    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = content
    )
}
