package ru.ldralighieri.composites.carbon.processor.model

import com.squareup.kotlinpoet.ClassName

internal val namedNavArgumentClassName = ClassName("androidx.navigation", "NamedNavArgument")
internal val navDeepLinkClassName = ClassName("androidx.navigation", "NavDeepLink")
internal val navBackStackEntryClassName = ClassName("androidx.navigation", "NavBackStackEntry")
internal val savedStateHandleClassName = ClassName("androidx.lifecycle", "SavedStateHandle")

internal val navTypeIntClassName =
    ClassName("androidx.navigation.NavType.Companion", "IntType")

internal val navTypeLongClassName =
    ClassName("androidx.navigation.NavType.Companion", "LongType")

internal val navTypeFloatClassName =
    ClassName("androidx.navigation.NavType.Companion", "FloatType")

internal val navTypeBooleanClassName =
    ClassName("androidx.navigation.NavType.Companion", "BoolType")

internal val navTypeStringClassName =
    ClassName("androidx.navigation.NavType.Companion", "StringType")