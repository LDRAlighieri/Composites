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

plugins {
    id("composites.application.compose")
    id("composites.ksp")
    id("composites.spotless")
}

android {
    namespace = "ru.ldralighieri.composites.sample"

    val buildTools: String by project
    @Suppress("LocalVariableName") val VERSION_NAME: String by project

    buildToolsVersion = buildTools

    defaultConfig {
        applicationId = "ru.ldralighieri.composites.sample"
        minSdk = 23
        versionCode = 1
        versionName = VERSION_NAME

        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        val debug by getting {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }

        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = debug.signingConfig
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    // Projects
    // Carbon
    implementation(projects.composites.compositesCarbon.core)
    ksp(projects.composites.compositesCarbon.processor)
    // Fiberglass
    implementation(projects.composites.compositesFiberglass)

    // Androidx
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling)

    // Google
    implementation(libs.google.material)
}
