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
    `kotlin-dsl`
}

group = "ru.ldralighieri.composites.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.spotless.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    implementation(libs.dokka.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("applicationCompose") {
            id = "composites.application.compose"
            implementationClass = "ApplicationComposeConventionPlugin"
        }

        register("dokka") {
            id = "composites.dokka"
            implementationClass = "DokkaConventionPlugin"
        }

        register("ksp") {
            id = "composites.ksp"
            implementationClass = "KspConventionPlugin"
        }

        register("libraryCompose") {
            id = "composites.library.compose"
            implementationClass = "LibraryComposeConventionPlugin"
        }

        register("spotless") {
            id = "composites.spotless"
            implementationClass = "SpotlessConventionPlugin"
        }
    }
}
