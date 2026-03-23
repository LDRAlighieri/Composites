import org.jetbrains.kotlin.gradle.dsl.JvmTarget

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
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.kotlin.multiplatform.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)
    compileOnly(libs.jetbrains.compose.gradlePlugin)
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
        register("composeMultiplatform") {
            id = "composites.compose.multiplatform"
            implementationClass = "ComposeMultiplatformConventionPlugin"
        }

        register("dokkaMultiplaform") {
            id = "composites.dokka.multiplatform"
            implementationClass = "DokkaMultiplatformConventionPlugin"
        }

        register("kotlinAndroidMultiplatform") {
            id = "composites.kotlin.android.multiplatform"
            implementationClass = "KotlinAndroidMultiplatformConventionPlugin"
        }

        register("kotlinCommonMultiplatform") {
            id = "composites.kotlin.common.multiplatform"
            implementationClass = "KotlinCommonMultiplatformConventionPlugin"
        }

        register("kotlinIosMultiplatform") {
            id = "composites.kotlin.ios.multiplatform"
            implementationClass = "KotlinIosMultiplatformConventionPlugin"
        }

        register("kotlinJvmMultiplatform") {
            id = "composites.kotlin.jvm.multiplatform"
            implementationClass = "KotlinJvmMultiplatformConventionPlugin"
        }

        register("kotlinWebMultiplatform") {
            id = "composites.kotlin.web.multiplatform"
            implementationClass = "KotlinWebMultiplatformConventionPlugin"
        }

        register("ksp") {
            id = "composites.ksp"
            implementationClass = "KspConventionPlugin"
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
