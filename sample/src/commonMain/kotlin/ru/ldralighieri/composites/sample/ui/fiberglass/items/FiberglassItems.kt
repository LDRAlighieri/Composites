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

@file:OptIn(ExperimentalUuidApi::class)

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

import ru.ldralighieri.composites.fiberglass.model.FiberglassItem
import ru.ldralighieri.composites.fiberglass.model.FiberglassStickyHeaderItem
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private val LOREM_IPSUM_SOURCE = (
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sodales laoreet commodo. " +
        "Phasellus a purus eu risus elementum consequat. Aenean eu elit ut nunc convallis " +
        "laoreet non ut libero. Suspendisse interdum placerat risus vel ornare. Donec vehicula, " +
        "turpis sed consectetur ullamcorper, ante nunc egestas quam, ultricies adipiscing velit " +
        "enim at nunc. Aenean id diam neque. Praesent ut lacus sed justo viverra fermentum et ut " +
        "sem. Fusce convallis gravida lacinia. Integer semper dolor ut elit sagittis lacinia. " +
        "Praesent sodales scelerisque eros at rhoncus. Duis posuere sapien vel ipsum ornare " +
        "interdum at eu quam. Vestibulum vel massa erat. Aenean quis sagittis purus. Phasellus " +
        "arcu purus, rutrum id consectetur non, bibendum at nibh. Duis nec erat dolor. Nulla " +
        "vitae consectetur ligula. Quisque nec mi est. Ut quam ante, rutrum at pellentesque " +
        "gravida, pretium in dui. Cras eget sapien velit. Suspendisse ut sem nec tellus vehicula " +
        "eleifend sit amet quis velit. Phasellus quis suscipit nisi. Nam elementum malesuada " +
        "tincidunt. Curabitur iaculis pretium eros, malesuada faucibus leo eleifend a. " +
        "Curabitur congue orci in neque euismod a blandit libero vehicula."
    ).split(" ")

internal data class StickyHeaderItem(override val title: String) : FiberglassStickyHeaderItem {
    override val id: String = title
}

internal data class SpacerItem(val height: Int) : FiberglassItem {
    override val id: String = Uuid.random().toString()
}

internal data class LoremIpsumItem(private val words: Int) : FiberglassItem {
    val text = LOREM_IPSUM_SOURCE.take(words).joinToString(separator = " ")
    override val id: Int = words
}

internal data class SmallImageItem(override val id: String = Uuid.random().toString()) : FiberglassItem

internal data class BigImageItem(override val id: String = Uuid.random().toString()) : FiberglassItem

internal data class ImagesRowItem(val count: Int) : FiberglassItem {
    override val id: String = "ImagesRowItem_$count"
    val images: List<FiberglassItem> =
        buildList {
            repeat(count) {
                add(SmallImageItem())
                add(BigImageItem())
            }
        }
}

internal open class TagItem(number: Int) : FiberglassItem {
    override val id: String = Uuid.random().toString()
    open val text: String = "tag $number"
}

internal data class SecondaryTagItem(val number: Int) : TagItem(number)

internal data class TertiaryTagItem(val number: Int) : TagItem(number)

internal data class TagsFlowRowItem(val count: Int) : FiberglassItem {
    override val id: String = "TagItem_$count"
    val tags: List<TagItem> =
        buildList {
            repeat(count) {
                val number = it + 1
                add(if (it % 2 == 0) SecondaryTagItem(number) else TertiaryTagItem(number))
            }
        }
}
