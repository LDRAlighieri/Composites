package ru.ldralighieri.composites.sample.ui

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import ru.ldralighieri.composites.sample.ui.app.CompositesApp

@Suppress("FunctionName")
fun MainViewController(): UIViewController = ComposeUIViewController { CompositesApp() }
