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

package ru.ldralighieri.composites.sample.ui.fiberglass.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.ldralighieri.composites.fiberglass.model.FiberglassLazyGridItemSlot
import ru.ldralighieri.composites.sample.theme.AppTheme
import kotlin.random.Random

private fun gridSlot(backgroundColor: Color): FiberglassLazyGridItemSlot =
    { _, _ ->
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(Random.nextInt(8, 48).dp)
                .clip(AppTheme.shapes.medium)
                .background(backgroundColor),
        )
    }

@Composable
internal fun primaryGridSlot(): FiberglassLazyGridItemSlot = gridSlot(AppTheme.colors.primary)

@Composable
internal fun errorGridSlot(): FiberglassLazyGridItemSlot = gridSlot(AppTheme.colors.error)
