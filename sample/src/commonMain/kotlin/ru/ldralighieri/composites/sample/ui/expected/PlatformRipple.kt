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

package ru.ldralighieri.composites.sample.ui.expected

import androidx.compose.foundation.Indication
import androidx.compose.material.RippleConfiguration
import androidx.compose.runtime.Composable

@get:Composable
expect val PlatformRippleConfiguration: RippleConfiguration?

@get:Composable
expect val PlatformClickIndication: Indication
