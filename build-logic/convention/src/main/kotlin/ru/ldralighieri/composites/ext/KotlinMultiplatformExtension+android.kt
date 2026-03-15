package ru.ldralighieri.composites.ext

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Action
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun KotlinMultiplatformExtension.android(
    configure: Action<KotlinMultiplatformAndroidLibraryTarget>
) {
    (this as ExtensionAware).extensions.configure("android", configure)
}
