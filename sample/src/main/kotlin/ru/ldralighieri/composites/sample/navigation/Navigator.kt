package ru.ldralighieri.composites.sample.navigation

import android.os.Parcelable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.ldralighieri.composites.carbon.core.Destination

class Navigator {

    private val _destinations = MutableSharedFlow<Event>(replay = 1)
    val destinations: SharedFlow<Event> = _destinations.asSharedFlow()

    fun navigateTo(destination: Destination) {
        _destinations.tryEmit(Event.ToDestination(destination))
    }

    fun navigateBack() { _destinations.tryEmit(Event.Back()) }

    sealed interface Event {
        data class ToDestination(val destination: Destination) : Event
        data class Back(val result: Parcelable? = null) : Event
    }
}

val LocalNavigator = staticCompositionLocalOf { Navigator() }
