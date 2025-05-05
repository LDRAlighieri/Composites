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
    alias(libs.plugins.composites.library.compose)
    alias(libs.plugins.composites.kotlin.multiplatform)
    alias(libs.plugins.composites.compose.multiplatform)
    alias(libs.plugins.composites.spotless)
    alias(libs.plugins.composites.dokka.multiplatform)
    alias(libs.plugins.composites.maven.publish.multiplatform)
}

android {
    namespace = "ru.ldralighieri.composites.fiberglass"
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_21.majorVersion))
    }

    sourceSets {
        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
        }

        androidMain.dependencies {
            api(libs.androidx.paging.compose.android)
        }
    }
}
