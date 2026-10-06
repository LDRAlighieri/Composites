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

package ru.ldralighieri.composites.fiberglass.column

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import ru.ldralighieri.composites.fiberglass.model.FiberglassItem
import ru.ldralighieri.composites.fiberglass.model.FiberglassLazyItemSlots
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
internal class FiberglassLazySlotDispatchTest {
    @Test
    fun missingSlotIsCheckedWhenTheItemIsComposedAfterScrolling() {
        var initialCompositionCompleted = false
        val exception = assertFailsWith<IllegalArgumentException> {
            runComposeUiTest {
                val items = List(100) { VisibleItem("item-$it") } + UnregisteredItem
                val slots: FiberglassLazyItemSlots = mapOf(
                    VisibleItem::class to { _, item ->
                        BasicText(item.id as String, Modifier.size(100.dp, 40.dp).testTag(item.id as String))
                    },
                )
                setContent {
                    FiberglassLazyColumn(items, slots, Modifier.size(300.dp).testTag("list"))
                }

                onNodeWithTag("item-0").assertTextEquals("item-0")
                initialCompositionCompleted = true
                onNodeWithTag("list").performScrollToIndex(100)
                waitForIdle()
            }
        }

        assertTrue(initialCompositionCompleted, "Off-screen items should not be validated eagerly")
        assertContains(exception.message.orEmpty(), UnregisteredItem::class.toString())
        assertContains(exception.message.orEmpty(), "position 100")
    }

    @Test
    fun explicitEmptySlotAllowsIntentionallyHiddenItems() = runComposeUiTest {
        val slots: FiberglassLazyItemSlots = mapOf(UnregisteredItem::class to { _, _ -> })
        setContent {
            FiberglassLazyColumn(listOf(UnregisteredItem), slots, Modifier.size(300.dp))
        }
        waitForIdle()
    }
}

private data class VisibleItem(override val id: String) : FiberglassItem

private data object UnregisteredItem : FiberglassItem {
    override val id: String = "unregistered"
}
