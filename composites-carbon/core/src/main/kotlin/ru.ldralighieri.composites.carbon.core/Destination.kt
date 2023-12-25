package ru.ldralighieri.composites.carbon.core

sealed interface Destination {
    data class Compose(val route: String) : Destination
}
