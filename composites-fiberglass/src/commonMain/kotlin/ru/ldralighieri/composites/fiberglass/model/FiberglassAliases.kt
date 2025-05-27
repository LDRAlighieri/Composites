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

package ru.ldralighieri.composites.fiberglass.model

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.runtime.Composable
import kotlin.reflect.KClass

/**
 * FiberglassLazyColumn and FiberglassLazyRow slot alias
 */
public typealias FiberglassLazyItemSlot =
    @Composable LazyItemScope.(position: Int, item: FiberglassItem) -> Unit

/**
 * FiberglassLazyColumn and FiberglassLazyRow slots map alias
 */
public typealias FiberglassLazyItemSlots = Map<KClass<out FiberglassItem>, FiberglassLazyItemSlot>

/**
 * FiberglassColumn slot alias
 */
public typealias FiberglassColumnItemSlot =
    @Composable ColumnScope.(position: Int, item: FiberglassItem) -> Unit

/**
 * FiberglassColumn sticky header slot alias
 */
public typealias FiberglassStickyHeaderSlot =
    @Composable LazyItemScope.(header: FiberglassStickyHeaderItem) -> Unit

/**
 * FiberglassColumn slots map alias
 */
public typealias FiberglassColumnItemSlots =
    Map<KClass<out FiberglassItem>, FiberglassColumnItemSlot>

/**
 * FiberglassRow slot alias
 */
public typealias FiberglassRowItemSlot =
    @Composable RowScope.(position: Int, item: FiberglassItem) -> Unit

/**
 * FiberglassRow slots map alias
 */
public typealias FiberglassRowItemSlots = Map<KClass<out FiberglassItem>, FiberglassRowItemSlot>

/**
 * Fiberglass[Vertical|Horizontal]Grid slot alias
 */
public typealias FiberglassLazyGridItemSlot =
    @Composable LazyGridItemScope.(position: Int, item: FiberglassItem) -> Unit

/**
 * Fiberglass[Vertical|Horizontal]Grid slots map alias
 */
public typealias FiberglassLazyGridItemSlots =
    Map<KClass<out FiberglassItem>, FiberglassLazyGridItemSlot>

/**
 * Fiberglass[Vertical|Horizontal]Grid slot alias
 */
public typealias FiberglassLazyStaggeredGridItemSlot =
    @Composable LazyStaggeredGridItemScope.(position: Int, item: FiberglassItem) -> Unit

/**
 * Fiberglass[Vertical|Horizontal]Grid slots map alias
 */
public typealias FiberglassLazyStaggeredGridItemSlots =
    Map<KClass<out FiberglassItem>, FiberglassLazyStaggeredGridItemSlot>
