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

    val targetSdk: String by project
    val buildTools: String by project
    val compileSdk: String by project
    val minSdk: String by project
    @Suppress("LocalVariableName") val VERSION_NAME: String by project

    this.compileSdk = compileSdk.toInt()
    buildToolsVersion = buildTools

    defaultConfig {
        this.targetSdk = targetSdk.toInt()
        applicationId = "ru.ldralighieri.composites.androidApp"
        this.minSdk = minSdk.toInt()
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
    implementation(projects.composites.sample)
    implementation(projects.composites.compositesFiberglass)

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)

    implementation(compose.material)
    implementation(compose.materialIconsExtended)
    implementation(compose.material3)
    implementation(compose.components.resources)
    implementation(compose.uiTooling)
    implementation(libs.compose.components.resources)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.google.material)
}
