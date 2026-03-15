@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

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

plugins {
    alias(libs.plugins.composites.library)
    alias(libs.plugins.composites.compose.multiplatform)
    alias(libs.plugins.composites.ksp)
    alias(libs.plugins.composites.spotless)
}

kotlin {

    android {
        namespace = "ru.ldralighieri.composites.sample"
        androidResources.enable = true
    }

    sourceSets {
        commonMain {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")

            dependencies {
                implementation(projects.composites.compositesCarbon.core)
                implementation(projects.composites.compositesFiberglass)

                api(compose.ui)
                api(compose.foundation)
                api(compose.animation)
                api(compose.components.resources)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
                implementation(compose.material3)

                implementation(libs.androidx.navigation.compose)
            }
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", projects.composites.compositesCarbon.processor)
}

// https://github.com/google/ksp/issues/567
tasks.withType<KotlinCompilationTask<*>>().all {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

compose.resources {
    publicResClass = true
}
