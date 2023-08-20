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

package ru.ldralighieri.composites

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import java.io.File

internal fun Project.configureAndroidCompose(
    extension: CommonExtension<*, *, *, *, *>,
) {
    extension.apply {
        buildFeatures.compose = true

        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("androidxComposeCompiler").get().toString()
        }

        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + buildComposeMetricsParameters()
        }
    }
}

private fun Project.buildComposeMetricsParameters(): List<String> = buildList {
    val enableMetricsProvider = project.providers.gradleProperty("enableComposeCompilerMetrics")
    if (enableMetricsProvider.orNull == "true") {
        val metricsFolder = File(project.layout.buildDirectory.asFile.get(), "compose-metrics")
        add("-P")
        add("plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination="
            + metricsFolder.absolutePath)
    }

    val enableReportsProvider = project.providers.gradleProperty("enableComposeCompilerReports")
    if (enableReportsProvider.orNull == "true") {
        val reportsFolder = File(project.layout.buildDirectory.asFile.get(), "compose-reports")
        add("-P")
        add("plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination="
            + reportsFolder.absolutePath)
    }
}
