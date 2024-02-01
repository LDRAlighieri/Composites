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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import ru.ldralighieri.composites.fiberglass.model.FiberglassItem
import ru.ldralighieri.composites.fiberglass.model.FiberglassLazyItemSlots
import ru.ldralighieri.composites.fiberglass.model.FiberglassStickyHeaderItem
import ru.ldralighieri.composites.fiberglass.model.FiberglassStickyHeaderSlot

/**
 * Fiberglass lazy column Composite
 *
 * @param items List of [FiberglassItem] items.
 * @param itemSlots FiberglassLazyColumn [slots map][FiberglassLazyItemSlots].
 * @param modifier The modifier to apply to this layout.
 * @param state The state object to be used to control or observe the list's state.
 * @param contentPadding A padding around the whole content.
 * @param reverseLayout Reverse the direction of scrolling and layout.
 * @param verticalArrangement The vertical arrangement of the layout's children.
 * @param horizontalAlignment The horizontal alignment applied to the items.
 * @param flingBehavior Logic describing fling behavior.
 * @param userScrollEnabled Whether the scrolling via the user gestures or accessibility actions
 * is allowed.
 */
@Composable
fun FiberglassLazyColumn(
    items: List<FiberglassItem>,
    itemSlots: FiberglassLazyItemSlots,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
    ) {
        itemsIndexed(
            items = items,
            key = { _, item -> item.id },
            contentType = { _, item -> item::class.simpleName },
        ) { position, item ->
            itemSlots[item::class]?.let { it(position, item) }
        }
    }
}

/**
 * Fiberglass lazy column Composite for [LazyPagingItems]
 *
 * @param items LazyPagingItems items.
 * @param itemSlots FiberglassLazyColumn [slots map][FiberglassLazyItemSlots]..
 * @param modifier The modifier to apply to this layout.
 * @param state The state object to be used to control or observe the list's state.
 * @param contentPadding A padding around the whole content.
 * @param reverseLayout Reverse the direction of scrolling and layout.
 * @param verticalArrangement The vertical arrangement of the layout's children.
 * @param horizontalAlignment The horizontal alignment applied to the items.
 * @param flingBehavior Logic describing fling behavior.
 * @param userScrollEnabled Whether the scrolling via the user gestures or accessibility actions
 * is allowed.
 */
@Composable
fun FiberglassLazyColumn(
    items: LazyPagingItems<out FiberglassItem>,
    itemSlots: FiberglassLazyItemSlots,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
    ) {
        items(
            count = items.itemCount,
            key = items.itemKey { it.id },
            contentType = items.itemContentType { it::class.simpleName },
        ) { position ->
            items[position]?.let { item ->
                itemSlots[item::class]?.let { slot -> slot(position, item) }
            }
        }
    }
}

/**
 * Fiberglass lazy column Composite with sticky headers
 *
 * @param sections Map of column sections.
 * @param headerSlot Sticky header [slot][FiberglassStickyHeaderSlot].
 * @param itemSlots FiberglassLazyColumn [slots map][FiberglassLazyItemSlots]..
 * @param modifier The modifier to apply to this layout.
 * @param state The state object to be used to control or observe the list's state.
 * @param contentPadding A padding around the whole content.
 * @param reverseLayout Reverse the direction of scrolling and layout.
 * @param verticalArrangement The vertical arrangement of the layout's children.
 * @param horizontalAlignment The horizontal alignment applied to the items.
 * @param flingBehavior Logic describing fling behavior.
 * @param userScrollEnabled Whether the scrolling via the user gestures or accessibility actions
 * is allowed.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FiberglassLazyColumn(
    sections: Map<FiberglassStickyHeaderItem, List<FiberglassItem>>,
    headerSlot: FiberglassStickyHeaderSlot,
    itemSlots: FiberglassLazyItemSlots,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
    ) {
        sections.forEach { (header, compositeItems) ->
            stickyHeader(
                key = header.id,
                contentType = header::class.simpleName,
            ) {
                headerSlot(header)
            }

            itemsIndexed(
                items = compositeItems,
                key = { _, item -> item.id },
                contentType = { _, item -> item::class.simpleName },
            ) { position, item ->
                itemSlots[item::class]?.let { it(position, item) }
            }
        }
    }
}
