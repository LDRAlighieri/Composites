/*
 * Copyright 2025 Vladimir Raupov
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

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import ru.ldralighieri.composites.carbon.core.Destination

internal enum class FiberglassType {
    Column,
    Grid,
    ;

    companion object {
        fun findByName(name: String?) = entries.firstOrNull { it.name == name } ?: entries.first()
    }
}

internal data object CompositesArgs

/*
adb shell am start \
    -a android.intent.action.VIEW \
    -d "composites://composites/fiberglass/<title>" ru.ldralighieri.composites.sample
 */
internal data class CompositesFiberglassArgs(
    val title: String,
)

/*
 adb shell am start \
    -a android.intent.action.VIEW \
    -d "composites://composites/fiberglass/example/<Column|Grid>" ru.ldralighieri.composites.sample
 */
internal data class CompositesFiberglassExampleArgs(
    val type: FiberglassType,
)

internal object CompositesRoute {
    @Suppress("ConstPropertyName")
    const val route: String = "composites"

    val arguments: List<NamedNavArgument> = emptyList()

    val deepLinks: List<NavDeepLink> = emptyList()

    fun create(): Destination.Compose = Destination.Compose("composites")
}

internal object CompositesFiberglassRoute {
    @Suppress("ConstPropertyName")
    const val route: String = "composites/fiberglass/{title}"

    val arguments: List<NamedNavArgument> = listOf(
        navArgument("title") {
            type = NavType.StringType
            nullable = false
            defaultValue = "Fiberglass composites"
        },
    )

    val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink {
            uriPattern = "composites://$route"
        },
    )

    fun create(title: String = "Fiberglass composites"): Destination.Compose =
        Destination.Compose("composites/fiberglass/$title")

    fun parseArguments(navBackStackEntry: NavBackStackEntry): CompositesFiberglassArgs =
        CompositesFiberglassArgs(
            title = navBackStackEntry.savedStateHandle.get<String>("title")
                ?: "Fiberglass composites",
        )

    fun parseArguments(savedStateHandle: SavedStateHandle): CompositesFiberglassArgs =
        CompositesFiberglassArgs(
            title = savedStateHandle["title"] ?: "Fiberglass composites",
        )
}

internal object CompositesFiberglassExampleRoute {
    @Suppress("ConstPropertyName")
    const val route: String = "composites/fiberglass/example/{type}"

    val arguments: List<NamedNavArgument> = listOf(
        navArgument("type") {
            type = NavType.StringType
            nullable = false
        },
    )

    val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink {
            uriPattern = "composites://$route"
        },
    )

    fun create(type: FiberglassType): Destination.Compose =
        Destination.Compose("composites/fiberglass/example/${type.name}")

    fun parseArguments(navBackStackEntry: NavBackStackEntry): CompositesFiberglassExampleArgs =
        CompositesFiberglassExampleArgs(
            type = FiberglassType.findByName(navBackStackEntry.savedStateHandle.get<String>("type")),
        )

    fun parseArguments(savedStateHandle: SavedStateHandle): CompositesFiberglassExampleArgs =
        CompositesFiberglassExampleArgs(
            type = FiberglassType.findByName(savedStateHandle["type"]),
        )
}
