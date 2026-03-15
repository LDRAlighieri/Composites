package ru.ldralighieri.composites.android.ui.fiberglass.items

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import ru.ldralighieri.composites.android.ThemePreviews
import ru.ldralighieri.composites.shared.ui.fiberglass.items.BigImageItem
import ru.ldralighieri.composites.shared.ui.fiberglass.items.ImagesRowItem
import ru.ldralighieri.composites.shared.ui.fiberglass.items.LoremIpsumItem
import ru.ldralighieri.composites.shared.ui.fiberglass.items.SecondaryTagItem
import ru.ldralighieri.composites.shared.ui.fiberglass.items.SmallImageItem
import ru.ldralighieri.composites.shared.ui.fiberglass.items.TagsFlowRowItem
import ru.ldralighieri.composites.shared.ui.fiberglass.items.bigImageSlot
import ru.ldralighieri.composites.shared.ui.fiberglass.items.imagesRowSlot
import ru.ldralighieri.composites.shared.ui.fiberglass.items.loremIpsumSlot
import ru.ldralighieri.composites.shared.ui.fiberglass.items.secondaryTagSlot
import ru.ldralighieri.composites.shared.ui.fiberglass.items.smallImageSlot
import ru.ldralighieri.composites.shared.ui.fiberglass.items.tagsFlowRowSlot
import ru.ldralighieri.composites.shared.ui.theme.AppTheme

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
