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

/**
 * Item interface for Fiberglass.
 *
 * This open interface makes no Compose stability promise about its implementations. Prefer
 * immutable models replaced through observable state, or back mutable UI properties with
 * Compose snapshot state.
 *
 * Apply `@Stable` or `@Immutable` only to concrete models that satisfy the respective contract,
 * including equality, property types and change notifications. An item's identity alone does
 * not establish the stability of its display data.
 */
public interface FiberglassItem {
    /**
     * Identity used by the default key factories. Keep the value and its equals/hashCode stable
     * for the same logical item, and follow the container's key uniqueness and saveability rules.
     */
    public val id: Any
}

/**
 * Sticky header interface for FiberglassLazyColumn, with the same state management requirements
 * as [FiberglassItem] and no additional Compose stability promise.
 *
 * A header's equals/hashCode must remain unchanged while it is used as a key in the sections map.
 */
public interface FiberglassStickyHeaderItem : FiberglassItem {
    /** Display title. Use snapshot state for in-place updates, or observably replace the header. */
    public val title: String
}
