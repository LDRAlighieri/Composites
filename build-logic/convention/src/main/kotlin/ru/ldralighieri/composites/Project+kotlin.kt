package ru.ldralighieri.composites

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun Project.kotlin(action: KotlinAndroidProjectExtension.() -> Unit) =
    extensions.configure(action)
