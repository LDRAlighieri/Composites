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
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.GradleDokkaSourceSetBuilder
import ru.ldralighieri.composites.action.getDokkaSourceStBuilderAction
import java.net.URI

@Suppress("unused")
internal class DokkaMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.dokka")

            val action: Action<GradleDokkaSourceSetBuilder> = getDokkaSourceStBuilderAction()

            tasks.withType<DokkaTask>().configureEach {
                dokkaSourceSets.named("commonMain", action)
                dokkaSourceSets.named("androidMain", action)
                dokkaSourceSets.named("iosMain", action)
            }
        }
    }
}
