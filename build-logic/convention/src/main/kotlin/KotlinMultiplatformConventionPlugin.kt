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

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
class KotlinMultiplatformConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.multiplatform")

        extensions.configure<KotlinMultiplatformExtension> {
            jvmToolchain {
                languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_21.majorVersion))
            }

            androidTarget()

            iosX64()
            iosArm64()
            iosSimulatorArm64()

            jvm()

            targets.all {
                compilations.all {
                    compileTaskProvider.configure {
                        compilerOptions {
                            freeCompilerArgs.addAll(buildCustomCompilerArgs())
                        }
                    }
                }
            }
        }
    }
}

private fun buildCustomCompilerArgs() = listOf(
    "-opt-in=kotlin.RequiresOptIn",
    "-Xcontext-receivers",
)
