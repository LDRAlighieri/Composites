package ru.ldralighieri.composites

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

@Suppress("unused")
internal fun Project.composeCompiler(block: ComposeCompilerGradlePluginExtension.() -> Unit) =
    extensions.configure<ComposeCompilerGradlePluginExtension>(block)
