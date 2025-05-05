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

import android.os.Parcelable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import ru.ldralighieri.composites.carbon.core.Destination

class Navigator {
    private val _destinations = Channel<Event>(Channel.CONFLATED)
    val destinations: Flow<Event> = _destinations.receiveAsFlow()

    fun navigateTo(destination: Destination) {
        _destinations.trySend(Event.ToDestination(destination))
    }

    fun navigateBack() {
        _destinations.trySend(Event.Back())
    }

    sealed interface Event {
        data class ToDestination(val destination: Destination) : Event

        data class Back(val result: Parcelable? = null) : Event
    }
}

val LocalNavigator = staticCompositionLocalOf { Navigator() }
