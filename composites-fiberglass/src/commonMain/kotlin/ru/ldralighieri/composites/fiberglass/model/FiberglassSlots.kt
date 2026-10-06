/*
 * Copyright 2026 Vladimir Raupov
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

import kotlin.reflect.KClass

internal fun <Slot : Any> Map<KClass<out FiberglassItem>, Slot>.requireSlot(
    item: FiberglassItem,
    position: Int,
): Slot = requireNotNull(this[item::class]) {
    "No Fiberglass slot registered for ${item::class} at position $position. " +
        "Register a slot for the exact runtime class; superclass and interface slots are not inherited."
}
