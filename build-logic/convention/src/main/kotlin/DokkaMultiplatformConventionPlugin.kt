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

import org.gradle.api.Action
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jetbrains.dokka.gradle.engine.parameters.DokkaSourceSetSpec
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@Suppress("unused")
internal class DokkaMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.dokka")

            extensions.configure(DokkaExtension::class.java) {
                moduleName.set("Composites")

                dokkaPublications.named("html") {
                    suppressInheritedMembers.set(true)
                    failOnWarning.set(true)
                }

                val action: Action<DokkaSourceSetSpec> = Action {
                    jdkVersion.set(JavaVersion.VERSION_21.majorVersion.toInt())

                    skipDeprecated.set(false)
                    reportUndocumented.set(false)
                    skipEmptyPackages.set(true)

                    sourceLink {
                        val relPath = rootProject.projectDir.toPath().relativize(projectDir.toPath())
                        localDirectory.set(file("src/main/kotlin"))
                        remoteUrl("https://github.com/LDRAlighieri/Composites/tree/master/$relPath/src/main/kotlin")
                        remoteLineSuffix.set("#L")
                    }

                    externalDocumentationLinks.register("reference") {
                        url("https://developer.android.com/reference/")
                        packageListUrl("https://developer.android.com/reference/package-list")
                    }

                    externalDocumentationLinks.register("reference-kotlin-androidx") {
                        url("https://developer.android.com/reference/kotlin/androidx/")
                        packageListUrl("https://developer.android.com/reference/kotlin/androidx/package-list")
                    }

                    externalDocumentationLinks.register("reference-kotlin-android-material") {
                        url("https://developer.android.com/reference/com/google/android/material/")
                        packageListUrl("https://developer.android.com/reference/com/google/android/material/package-list")
                    }
                }

                val currentSourceSets: Set<String> = buildSet {
                    kotlin {
                        addAll(
                            sourceSets
                                .map(KotlinSourceSet::getName)
                                .filter { name -> name.endsWith("Main") })
                    }
                }

                currentSourceSets.forEach {
                    dokkaSourceSets.named(it, action)
                }
            }
        }
    }
}

private fun Project.kotlin(configure: Action<KotlinMultiplatformExtension>) =
    (this as ExtensionAware).extensions.configure("kotlin", configure)
