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

package ru.ldralighieri.composites.fiberglass.column

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.ldralighieri.composites.fiberglass.model.FiberglassColumnItemSlots
import ru.ldralighieri.composites.fiberglass.model.FiberglassItem

/**
 * Fiberglass flow column Composite
 *
 * @param items List of [FiberglassItem] items.
 * @param itemSlots FiberglassColumn [slots map][FiberglassColumnItemSlots].
 * @param modifier The modifier to be applied to the Composite.
 * @param contentPadding A padding around the whole content. Negative padding is not permitted — it
 * will cause IllegalArgumentException.
 * @param verticalArrangement The vertical arrangement of the layout's children.
 * @param horizontalArrangement The horizontal arrangement of the layout's virtual columns.
 * @param maxItemsInEachColumn The maximum number of items per column
 */
@Composable
fun FiberglassFlowColumn(
    items: List<FiberglassItem>,
    itemSlots: FiberglassColumnItemSlots,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    maxItemsInEachColumn: Int = Int.MAX_VALUE
) {
    Box(modifier = modifier) {
        FlowColumn(
            modifier = Modifier.padding(contentPadding),
            verticalArrangement = verticalArrangement,
            horizontalArrangement = horizontalArrangement,
            maxItemsInEachColumn = maxItemsInEachColumn
        ) {
            items.forEach { item ->
                key(item.id) {
                    itemSlots[item::class]?.let { it(item) }
                }
            }
        }
    }
}
