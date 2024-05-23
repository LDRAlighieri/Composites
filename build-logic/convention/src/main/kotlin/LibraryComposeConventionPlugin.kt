@file:Suppress("UnstableApiUsage")

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

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import ru.ldralighieri.composites.configureAndroidCompose
import ru.ldralighieri.composites.configureKotlinAndroid
import ru.ldralighieri.composites.getLibrary
import ru.ldralighieri.composites.libs

@Suppress("unused")
internal class LibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("kotlin-android")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("com.vanniktech.maven.publish")
            }

            extensions.configure<LibraryExtension> {
                val targetSdk: String by project
                defaultConfig.targetSdk = targetSdk.toInt()

                configureKotlinAndroid(this)
                configureAndroidCompose(this)

                buildTypes {
                    release {
                        proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
                    }
                }
            }

            dependencies {
                // Compose
                add("implementation", platform(libs.composeBom()))
                add("implementation", libs.composeRuntime())
                add("implementation", libs.composeFoundation())
                add("implementation", libs.composeUiTooling())
            }
        }
    }
}

// Compose
private fun VersionCatalog.composeBom() = getLibrary("androidx.compose.bom")
private fun VersionCatalog.composeRuntime() = getLibrary("androidx.compose.runtime")
private fun VersionCatalog.composeFoundation() = getLibrary("androidx.compose.foundation")
private fun VersionCatalog.composeUiTooling() = getLibrary("androidx.compose.ui.tooling")
