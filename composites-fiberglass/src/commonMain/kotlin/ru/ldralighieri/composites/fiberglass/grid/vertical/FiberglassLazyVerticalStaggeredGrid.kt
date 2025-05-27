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

package ru.ldralighieri.composites.fiberglass.grid.vertical

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.ldralighieri.composites.fiberglass.model.FiberglassItem
import ru.ldralighieri.composites.fiberglass.model.FiberglassLazyStaggeredGridItemSlots

/**
 * Fiberglass lazy vertical staggered grid Composite
 *
 * @param items List of [FiberglassItem] items.
 * @param itemSlots FiberglassLazyHorizontalStaggeredGrid
 * [slots map][FiberglassLazyStaggeredGridItemSlots].
 * @param modifier The modifier to apply to this layout.
 * @param state The state object that can be used to control and observe staggered grid state.
 * @param contentPadding A padding around the whole content.
 * @param reverseLayout Reverse the direction of scrolling and layout.
 * @param verticalItemSpacing Vertical spacing between items.
 * @param horizontalArrangement Arrangement specifying horizontal spacing between.
 * @param flingBehavior Logic describing fling behavior.
 * @param userScrollEnabled Whether scroll with gestures or accessibility actions are allowed. It
 * is still possible to scroll programmatically through state when [userScrollEnabled] is set to
 * false.
 * @param itemKey A factory of stable and unique keys representing the item.
 * @param itemContentType A factory of the content types for the item.
 */
@ExperimentalFoundationApi
@Composable
public fun FiberglassLazyVerticalStaggeredGrid(
    items: List<FiberglassItem>,
    itemSlots: FiberglassLazyStaggeredGridItemSlots,
    columns: StaggeredGridCells,
    modifier: Modifier = Modifier,
    state: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalItemSpacing: Dp = 0.dp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(0.dp),
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    itemKey: ((position: Int, item: FiberglassItem) -> Any)? = { _, item -> item.id },
    itemContentType: (position: Int, item: FiberglassItem) -> Any? = { _, item ->
        item::class.simpleName
    },
) {
    LazyVerticalStaggeredGrid(
        columns = columns,
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalItemSpacing = verticalItemSpacing,
        horizontalArrangement = horizontalArrangement,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
    ) {
        itemsIndexed(
            items = items,
            key = itemKey,
            contentType = itemContentType,
        ) { position, item ->
            itemSlots[item::class]?.let { it(position, item) }
        }
    }
}
