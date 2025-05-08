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

package ru.ldralighieri.composites.sample

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import composites.sample.generated.resources.Res
import composites.sample.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource
import ru.ldralighieri.composites.sample.ui.app.CompositesApp

fun main() = application {
    Window(
        title = stringResource(Res.string.app_name),
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = 400.dp, height = 800.dp),
        onKeyEvent = { event -> event.key == Key.Escape && event.type == KeyEventType.KeyUp },
    ) { CompositesApp() }
}
