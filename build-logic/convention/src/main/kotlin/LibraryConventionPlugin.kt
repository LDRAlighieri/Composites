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
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.provideDelegate

@Suppress("unused")
internal class LibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")

            extensions.configure<LibraryExtension> {
                val targetSdk: String by project
                val compileSdk: String by project
                val minSdk: String by project

                defaultConfig.targetSdk = targetSdk.toInt()
                this.compileSdk = compileSdk.toInt()
                defaultConfig {
                    this.minSdk = minSdk.toInt()
                }

                buildTypes {
                    release {
                        proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
                    }
                }
            }
        }
    }
}
