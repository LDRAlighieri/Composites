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

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.LocalSaveableStateRegistry
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
 * @param itemKey A factory of stable and unique keys representing the item.
 * @param itemContentType A factory of the content types for the item.
 */
@Composable
public fun FiberglassLazyColumn(
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
    itemKey: ((position: Int, item: FiberglassItem) -> Any)? = { _, item -> item.id },
    itemContentType: (position: Int, item: FiberglassItem) -> Any? = { _, item ->
        item::class.simpleName
    },
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
            key = itemKey,
            contentType = itemContentType,
        ) { position, item ->
            itemSlots[item::class]?.let { it(position, item) }
        }
    }
}

/**
 * Fiberglass lazy column Composite with sticky headers
 *
 * Keys are scoped by section identity and record kind, so headers and items may share IDs,
 * and item IDs may repeat across sections. Stable keys preserve state on section and item
 * reordering. Moving an item to another section gives it a new identity.
 * Each key component must support Android Bundle state saving and have stable equals/hashCode.
 *
 * @param sections Map of column sections in iteration order. Section identities must be unique.
 * @param headerSlot Sticky header [slot][FiberglassStickyHeaderSlot].
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
 * @param headerKey A factory of stable section identities, unique across sections, receiving the
 * section index. Defaults to header.id. A null factory or result uses a positional header key;
 * items then use header.id as their section identity. Header keys are scoped by record kind.
 * @param headerContentType A factory of the content types for the header.
 * @param itemKey A factory of stable keys, unique within their section, receiving the item index
 * within that section. Defaults to item.id. Keys are scoped by section identity and record kind,
 * including custom keys. A null factory uses positional item keys. Positional keys do not
 * guarantee state preservation on reorder.
 * @param itemContentType A factory of the content types for the item.
 */
@Composable
public fun FiberglassLazyColumn(
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
    headerKey: ((position: Int, header: FiberglassStickyHeaderItem) -> Any?)? = { _, header ->
        header.id
    },
    headerContentType: ((position: Int, header: FiberglassStickyHeaderItem) -> Any?)? =
        { _, header -> header::class.simpleName },
    itemKey: ((position: Int, item: FiberglassItem) -> Any)? = { _, item -> item.id },
    itemContentType: (position: Int, item: FiberglassItem) -> Any? = { _, item ->
        item::class.simpleName
    },
) {
    val saveableStateRegistry = LocalSaveableStateRegistry.current

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
        sections.onEachIndexed { sectionPosition, (header, compositeItems) ->
            val sectionKey = headerKey?.invoke(sectionPosition, header)
            val sectionIdentity = sectionKey ?: header.id
            if (sectionKey != null || (itemKey != null && compositeItems.isNotEmpty())) {
                require(saveableStateRegistry?.canBeSaved(sectionIdentity) != false) {
                    "Section keys must be saveable by the current SaveableStateRegistry."
                }
            }

            stickyHeader(
                key = sectionKey?.let { arrayListOf("header", it) },
                contentType = headerContentType?.invoke(sectionPosition, header),
            ) {
                headerSlot(header)
            }

            itemsIndexed(
                items = compositeItems,
                key = itemKey?.let { keyFactory ->
                    { position, item ->
                        val localKey = keyFactory(position, item)
                        require(saveableStateRegistry?.canBeSaved(localKey) != false) {
                            "Item keys must be saveable by the current SaveableStateRegistry."
                        }
                        arrayListOf("item", sectionIdentity, localKey)
                    }
                },
                contentType = itemContentType,
            ) { itemPosition, item ->
                itemSlots[item::class]?.let { it(itemPosition, item) }
            }
        }
    }
}
