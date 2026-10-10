@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

/*
 * Copyright 2026 Vladimir Raupov
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
    id("com.android.application")
    alias(libs.plugins.composites.compose.multiplatform)
    alias(libs.plugins.composites.ksp)
    alias(libs.plugins.composites.spotless)
}

android {
    namespace = "ru.ldralighieri.composites.android"

    compileSdk = providers.gradleProperty("compileSdk").get().toInt()
    buildToolsVersion = providers.gradleProperty("buildTools").get()

    defaultConfig {
        targetSdk = providers.gradleProperty("targetSdk").get().toInt()
        applicationId = "ru.ldralighieri.composites.androidApp"
        minSdk = providers.gradleProperty("minSdk").get().toInt()
        versionCode = 1
        versionName = providers.gradleProperty("VERSION_NAME").get()

        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        val debug = getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }

        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = debug.signingConfig
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures.compose = true
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_21.majorVersion))
    }
}

dependencies {
    implementation(projects.composites.shared)
    implementation(projects.composites.compositesFiberglass)

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)

    implementation(libs.compose.components.resources)
    implementation(libs.compose.material)
    implementation(libs.compose.materialIconsExtended)
    implementation(libs.compose.material3)
    implementation(libs.compose.uiTooling)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.google.material)
}
