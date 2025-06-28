
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
    toolchain {
        languageVersion = JavaLanguageVersion.of(JavaVersion.VERSION_21.majorVersion)
    }
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)
    compileOnly(libs.jetbrains.compose.gradlePlugin)
    compileOnly(libs.jetbrains.compose.hotReload.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.spotless.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.maven.publish.gradlePlugin)
    implementation(libs.dokka.gradlePlugin)

    implementation(files((libs as Any).javaClass.superclass.protectionDomain.codeSource.location))
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        plugins {
            register("composeMultiplatform") {
                id = "composites.compose.multiplatform"
                implementationClass = "ComposeMultiplatformConventionPlugin"
            }
        }

        register("dokkaMultiplaform") {
            id = "composites.dokka.multiplatform"
            implementationClass = "DokkaMultiplatformConventionPlugin"
        }

        plugins {
            register("kotlinMultiplatform") {
                id = "composites.kotlin.multiplatform"
                implementationClass = "KotlinMultiplatformConventionPlugin"
            }
        }

        register("ksp") {
            id = "composites.ksp"
            implementationClass = "KspConventionPlugin"
        }

        register("libraryCompose") {
            id = "composites.library.compose"
            implementationClass = "LibraryComposeConventionPlugin"
        }

        register("library") {
            id = "composites.library"
            implementationClass = "LibraryConventionPlugin"
        }

        register("mavenPublishMultiplatform") {
            id = "composites.maven.publish.multiplatform"
            implementationClass = "MavenPublishMultiplatformConventionPlugin"
        }

        register("spotless") {
            id = "composites.spotless"
            implementationClass = "SpotlessConventionPlugin"
        }
    }
}
