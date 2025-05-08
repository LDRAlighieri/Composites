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

package ru.ldralighieri.composites.sample.ui.fiberglass.items

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import ru.ldralighieri.composites.sample.ThemePreviews
import ru.ldralighieri.composites.sample.ui.theme.AppTheme

@Composable
@ThemePreviews
private fun LoremIpsumSlotPreview() {
    ItemSlotPreview {
        loremIpsumSlot()(0, LoremIpsumItem(20))
    }
}

@Composable
@ThemePreviews
private fun SmallImageSlotPreview() {
    ItemSlotPreview {
        smallImageSlot()(0, SmallImageItem())
    }
}

@Composable
@ThemePreviews
private fun BigImageSlotPreview() {
    ItemSlotPreview {
        bigImageSlot()(0, BigImageItem())
    }
}

@Composable
@ThemePreviews
private fun ImagesRowSlotPreview() {
    ItemSlotPreview {
        imagesRowSlot()(0, ImagesRowItem(2))
    }
}

@Composable
@ThemePreviews
private fun TagSlotPreview() {
    AppTheme {
        Row {
            secondaryTagSlot()(0, SecondaryTagItem(2))
        }
    }
}

@Composable
@ThemePreviews
private fun TagsRowSlotPreview() {
    ItemSlotPreview {
        tagsFlowRowSlot()(0, TagsFlowRowItem(2))
    }
}

@Composable
private fun ItemSlotPreview(content: @Composable LazyItemScope.() -> Unit) {
    AppTheme {
        LazyColumn {
            item {
                content()
            }
        }
    }
}
