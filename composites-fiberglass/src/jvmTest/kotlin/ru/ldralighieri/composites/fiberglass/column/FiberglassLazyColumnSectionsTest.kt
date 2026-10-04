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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.LocalSaveableStateRegistry
import androidx.compose.runtime.saveable.SaveableStateRegistry
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import ru.ldralighieri.composites.fiberglass.model.FiberglassItem
import ru.ldralighieri.composites.fiberglass.model.FiberglassLazyItemSlots
import ru.ldralighieri.composites.fiberglass.model.FiberglassStickyHeaderItem
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
internal class FiberglassLazyColumnSectionsTest {
    @Test
    fun defaultKeysSeparateHeadersAndRepeatedItemsAcrossSections() = runComposeUiTest {
        val repeatedItem = TestItem(id = "shared", label = "item")
        val sections = linkedMapOf<FiberglassStickyHeaderItem, List<FiberglassItem>>(
            TestHeader(id = "shared", title = "first") to listOf(repeatedItem),
            TestHeader(id = "second", title = "second") to listOf(repeatedItem),
        )

        setContent { SectionList(sections) }

        onNodeWithTag("first").assertTextEquals("first:0")
        onNodeWithTag("second").assertTextEquals("second:0")
        onAllNodesWithTag("item").assertCountEquals(2)
    }

    @Test
    fun customKeysAreLocalToTheirSectionAndEntryKind() = runComposeUiTest {
        val sections = linkedMapOf<FiberglassStickyHeaderItem, List<FiberglassItem>>(
            TestHeader(id = "first-id", title = "first") to
                listOf(TestItem(id = "first-item-id", label = "first:item")),
            TestHeader(id = "second-id", title = "second") to
                listOf(TestItem(id = "second-item-id", label = "second:item")),
        )

        setContent {
            SectionList(
                sections = sections,
                headerKey = { _, header -> header.title },
                itemKey = { _, _ -> "first" },
            )
        }

        onNodeWithTag("first").assertTextEquals("first:0")
        onNodeWithTag("first:item").assertTextEquals("first:item:0")
        onNodeWithTag("second:item").assertTextEquals("second:item:0")
    }

    @Test
    fun keysRetainTheirTypesAndEqualityInsteadOfStringOrHashRepresentations() = runComposeUiTest {
        val localIds = listOf(2, "2", "Aa", "BB")
        val sections = linkedMapOf<FiberglassStickyHeaderItem, List<FiberglassItem>>(
            TestHeader(id = 1, title = "integer") to localIds.mapIndexed { position, id ->
                TestItem(id = id, label = "integer:$position")
            },
            TestHeader(id = "1", title = "string") to localIds.mapIndexed { position, id ->
                TestItem(id = id, label = "string:$position")
            },
        )

        setContent { SectionList(sections) }

        onNodeWithTag("integer").assertTextEquals("integer:0")
        onNodeWithTag("string").assertTextEquals("string:0")
        localIds.indices.forEach { position ->
            onNodeWithTag("integer:$position").assertTextEquals("integer:$position:0")
            onNodeWithTag("string:$position").assertTextEquals("string:$position:0")
        }
    }

    @Test
    fun savedStateFollowsSectionAndItemIdentityAfterReordering() = runComposeUiTest {
        val firstHeader = TestHeader(id = "first", title = "first")
        val secondHeader = TestHeader(id = "second", title = "second")
        val firstItems = listOf(
            TestItem(id = "shared", label = "first:shared"),
            TestItem(id = "other", label = "first:other"),
        )
        val secondItems = listOf(
            TestItem(id = "shared", label = "second:shared"),
            TestItem(id = "other", label = "second:other"),
        )
        var sections by mutableStateOf(
            linkedMapOf<FiberglassStickyHeaderItem, List<FiberglassItem>>(firstHeader to firstItems, secondHeader to secondItems),
            referentialEqualityPolicy(),
        )

        setContent { SectionList(sections) }

        onNodeWithTag("first").performClick()
        onNodeWithTag("first:shared").performClick()
        onNodeWithTag("second:shared").performClick().performClick()

        runOnIdle {
            sections = linkedMapOf<FiberglassStickyHeaderItem, List<FiberglassItem>>(
                secondHeader to secondItems.reversed(),
                firstHeader to firstItems.reversed(),
            )
        }

        onNodeWithTag("first").assertTextEquals("first:1")
        onNodeWithTag("second").assertTextEquals("second:0")
        onNodeWithTag("first:shared").assertTextEquals("first:shared:1")
        onNodeWithTag("second:shared").assertTextEquals("second:shared:2")
        onNodeWithTag("first:other").assertTextEquals("first:other:0")
        onNodeWithTag("second:other").assertTextEquals("second:other:0")
        assertTrue(
            onNodeWithTag("second").fetchSemanticsNode().boundsInRoot.top <
                onNodeWithTag("first").fetchSemanticsNode().boundsInRoot.top,
        )
        assertTrue(
            onNodeWithTag("second:other").fetchSemanticsNode().boundsInRoot.top <
                onNodeWithTag("second:shared").fetchSemanticsNode().boundsInRoot.top,
        )
    }

    @Test
    fun customKeysPreserveStateWhenModelIdsChange() = runComposeUiTest {
        var sections by mutableStateOf(
            linkedMapOf<FiberglassStickyHeaderItem, List<FiberglassItem>>(
                TestHeader(id = 1, title = "first") to listOf(TestItem(id = 1, label = "first:item")),
                TestHeader(id = 2, title = "second") to listOf(TestItem(id = 2, label = "second:item")),
            ),
            referentialEqualityPolicy(),
        )

        setContent {
            SectionList(
                sections = sections,
                headerKey = { _, header -> header.title },
                itemKey = { _, item -> (item as TestItem).label.substringAfter(':') },
            )
        }

        onNodeWithTag("first").performClick()
        onNodeWithTag("first:item").performClick()
        onNodeWithTag("second:item").performClick().performClick()
        runOnIdle {
            sections = linkedMapOf<FiberglassStickyHeaderItem, List<FiberglassItem>>(
                TestHeader(id = 20, title = "second") to listOf(TestItem(id = 20, label = "second:item")),
                TestHeader(id = 10, title = "first") to listOf(TestItem(id = 10, label = "first:item")),
            )
        }

        onNodeWithTag("first").assertTextEquals("first:1")
        onNodeWithTag("first:item").assertTextEquals("first:item:1")
        onNodeWithTag("second:item").assertTextEquals("second:item:2")
    }

    @Test
    fun movingAnItemToAnotherSectionCreatesNewSavedState() = runComposeUiTest {
        val sourceHeader = TestHeader(id = "source", title = "source")
        val targetHeader = TestHeader(id = "target", title = "target")
        val item = TestItem(id = "item", label = "item")
        var sections by mutableStateOf(
            linkedMapOf<FiberglassStickyHeaderItem, List<FiberglassItem>>(sourceHeader to listOf(item), targetHeader to emptyList()),
            referentialEqualityPolicy(),
        )

        setContent { SectionList(sections) }

        onNodeWithTag("item").performClick().performClick()
        onNodeWithTag("item").assertTextEquals("item:2")
        runOnIdle {
            sections = linkedMapOf<FiberglassStickyHeaderItem, List<FiberglassItem>>(sourceHeader to emptyList(), targetHeader to listOf(item))
        }

        onNodeWithTag("item").assertTextEquals("item:0")
    }

    @Test
    fun nullHeaderCallbackKeepsHeadersPositionalAndItemsScopedByHeaderId() = runComposeUiTest {
        val firstHeader = TestHeader(id = "first", title = "first")
        val secondHeader = TestHeader(id = "second", title = "second")
        val firstItems = listOf(TestItem(id = "shared", label = "first:item"))
        val secondItems = listOf(TestItem(id = "shared", label = "second:item"))
        var sections by mutableStateOf(
            linkedMapOf<FiberglassStickyHeaderItem, List<FiberglassItem>>(firstHeader to firstItems, secondHeader to secondItems),
            referentialEqualityPolicy(),
        )

        setContent { SectionList(sections, headerKey = null) }

        onNodeWithTag("first").performClick()
        onNodeWithTag("first:item").performClick()
        runOnIdle {
            // Map equality ignores iteration order, so model changes also trigger recomposition.
            sections = linkedMapOf(
                secondHeader.copy(revision = 1) to secondItems,
                firstHeader.copy(revision = 1) to firstItems,
            )
        }

        onNodeWithTag("second").assertTextEquals("second:1")
        onNodeWithTag("first").assertTextEquals("first:0")
        onNodeWithTag("first:item").assertTextEquals("first:item:1")
        onNodeWithTag("second:item").assertTextEquals("second:item:0")
        assertTrue(
            onNodeWithTag("second").fetchSemanticsNode().boundsInRoot.top <
                onNodeWithTag("first").fetchSemanticsNode().boundsInRoot.top,
        )
    }

    @Test
    fun nullHeaderKeyResultUsesHeaderIdForItemIdentity() = runComposeUiTest {
        val firstHeader = TestHeader(id = "first", title = "first")
        val secondHeader = TestHeader(id = "second", title = "second")
        val firstItems = listOf(TestItem(id = "shared", label = "first:item"))
        val secondItems = listOf(TestItem(id = "shared", label = "second:item"))
        var sections by mutableStateOf(
            linkedMapOf<FiberglassStickyHeaderItem, List<FiberglassItem>>(firstHeader to firstItems, secondHeader to secondItems),
            referentialEqualityPolicy(),
        )

        setContent { SectionList(sections, headerKey = { _, _ -> null }) }

        onNodeWithTag("first:item").performClick()
        runOnIdle {
            sections = linkedMapOf(
                secondHeader.copy(revision = 1) to secondItems,
                firstHeader.copy(revision = 1) to firstItems,
            )
        }

        onNodeWithTag("first:item").assertTextEquals("first:item:1")
        onNodeWithTag("second:item").assertTextEquals("second:item:0")
        assertTrue(
            onNodeWithTag("second").fetchSemanticsNode().boundsInRoot.top <
                onNodeWithTag("first").fetchSemanticsNode().boundsInRoot.top,
        )
    }

    @Test
    fun nullItemCallbackPreservesPositionalItemState() = runComposeUiTest {
        val header = TestHeader(id = "header", title = "header")
        val first = TestItem(id = "same", label = "first")
        val second = TestItem(id = "same", label = "second")
        var sections by mutableStateOf(
            linkedMapOf<FiberglassStickyHeaderItem, List<FiberglassItem>>(header to listOf(first, second)),
            referentialEqualityPolicy(),
        )

        setContent { SectionList(sections, itemKey = null) }

        onNodeWithTag("first").performClick()
        runOnIdle { sections = linkedMapOf<FiberglassStickyHeaderItem, List<FiberglassItem>>(header to listOf(second, first)) }

        onNodeWithTag("second").assertTextEquals("second:1")
        onNodeWithTag("first").assertTextEquals("first:0")
    }

    @Test
    fun callbacksContinueToReceiveSectionAndLocalItemPositions() = runComposeUiTest {
        val sections = linkedMapOf<FiberglassStickyHeaderItem, List<FiberglassItem>>(
            TestHeader(id = "first", title = "first") to listOf(
                TestItem(id = "one", label = "first:one"),
                TestItem(id = "two", label = "first:two"),
            ),
            TestHeader(id = "second", title = "second") to listOf(
                TestItem(id = "one", label = "second:one"),
                TestItem(id = "two", label = "second:two"),
            ),
        )
        val headerKeys = mutableSetOf<Pair<Int, Any>>()
        val itemKeys = mutableSetOf<Pair<Int, String>>()
        val headerTypes = mutableSetOf<Pair<Int, Any>>()
        val itemTypes = mutableSetOf<Pair<Int, String>>()
        val slots: FiberglassLazyItemSlots = mapOf(
            TestItem::class to { position, item ->
                val label = (item as TestItem).label
                BasicText("$label:$position", Modifier.height(32.dp).testTag(label))
            },
        )

        setContent {
            FiberglassLazyColumn(
                sections = sections,
                headerSlot = { BasicText(it.title, Modifier.height(32.dp)) },
                itemSlots = slots,
                modifier = Modifier.size(300.dp, 400.dp),
                headerKey = { position, header ->
                    headerKeys += position to header.id
                    header.id
                },
                itemKey = { position, item ->
                    itemKeys += position to (item as TestItem).label
                    item.id
                },
                headerContentType = { position, header ->
                    headerTypes += position to header.id
                    "header"
                },
                itemContentType = { position, item ->
                    itemTypes += position to (item as TestItem).label
                    "item"
                },
            )
        }

        onNodeWithTag("first:one").assertTextEquals("first:one:0")
        onNodeWithTag("first:two").assertTextEquals("first:two:1")
        onNodeWithTag("second:one").assertTextEquals("second:one:0")
        onNodeWithTag("second:two").assertTextEquals("second:two:1")
        runOnIdle {
            val expectedHeaders: Set<Pair<Int, Any>> = setOf(0 to "first", 1 to "second")
            val expectedItems = setOf(
                0 to "first:one",
                1 to "first:two",
                0 to "second:one",
                1 to "second:two",
            )
            assertEquals(expectedHeaders, headerKeys)
            assertEquals(expectedHeaders, headerTypes)
            assertEquals(expectedItems, itemKeys)
            assertEquals(expectedItems, itemTypes)
        }
    }

    @Test
    fun composedKeysSupportRestrictedSaveabilityAndStateRestoration() = runComposeUiTest {
        val sections = linkedMapOf<FiberglassStickyHeaderItem, List<FiberglassItem>>(
            TestHeader(id = "first", title = "first") to
                listOf(TestItem(id = "shared", label = "first:item")),
            TestHeader(id = "second", title = "second") to
                listOf(TestItem(id = "shared", label = "second:item")),
        )
        var registry by mutableStateOf(SaveableStateRegistry(null, ::isSaveableValue))
        var showList by mutableStateOf(true)

        setContent {
            if (showList) {
                CompositionLocalProvider(LocalSaveableStateRegistry provides registry) {
                    SectionList(sections)
                }
            }
        }

        onNodeWithTag("first").performClick()
        onNodeWithTag("first:item").performClick()
        onNodeWithTag("second:item").performClick().performClick()
        lateinit var savedValues: Map<String, List<Any?>>
        runOnIdle {
            savedValues = registry.performSave()
            showList = false
        }
        waitForIdle()
        runOnIdle {
            registry = SaveableStateRegistry(savedValues, ::isSaveableValue)
            showList = true
        }

        onNodeWithTag("first").assertTextEquals("first:1")
        onNodeWithTag("first:item").assertTextEquals("first:item:1")
        onNodeWithTag("second:item").assertTextEquals("second:item:2")
    }

    @Test
    fun emptySectionWithPositionalHeaderDoesNotUseItsIdAsASaveableKey() = runComposeUiTest {
        val registry = SaveableStateRegistry(null) { it !is UnsupportedKey }
        setContent {
            CompositionLocalProvider(LocalSaveableStateRegistry provides registry) {
                SectionList(
                    sections = mapOf(TestHeader(UnsupportedKey("unused"), "empty") to emptyList()),
                    headerKey = null,
                )
            }
        }

        onNodeWithTag("empty").assertTextEquals("empty:0")
    }

    @Test
    fun unsupportedSectionKeyIsRejectedByTheSaveabilityRegistry() {
        assertFailsWith<IllegalArgumentException> {
            runComposeUiTest {
                setContent {
                    RestrictedSaveability(checkContents = false) {
                        SectionList(
                            sections = mapOf(
                                TestHeader(id = UnsupportedKey("header"), title = "header") to
                                    listOf(TestItem(id = "item", label = "item")),
                            ),
                        )
                    }
                }
                waitForIdle()
            }
        }
    }

    @Test
    fun unsupportedLocalItemKeyIsRejectedByTheSaveabilityRegistry() {
        assertFailsWith<IllegalArgumentException> {
            runComposeUiTest {
                setContent {
                    RestrictedSaveability(checkContents = false) {
                        SectionList(
                            sections = mapOf(
                                TestHeader(id = "header", title = "header") to
                                    listOf(TestItem(id = UnsupportedKey("item"), label = "item")),
                            ),
                        )
                    }
                }
                waitForIdle()
            }
        }
    }
}

@Composable
private fun RestrictedSaveability(checkContents: Boolean = true, content: @Composable () -> Unit) {
    val restrictedRegistry = remember(checkContents) {
        SaveableStateRegistry(null) { value ->
            if (!checkContents && (value is List<*> || value is Map<*, *>)) {
                // Android accepts the composite container without checking its components.
                true
            } else {
                isSaveableValue(value)
            }
        }
    }
    CompositionLocalProvider(LocalSaveableStateRegistry provides restrictedRegistry, content = content)
}

@Composable
private fun SectionList(
    sections: Map<FiberglassStickyHeaderItem, List<FiberglassItem>>,
    headerKey: ((Int, FiberglassStickyHeaderItem) -> Any?)? = { _, header -> header.id },
    itemKey: ((Int, FiberglassItem) -> Any)? = { _, item -> item.id },
) {
    val slots: FiberglassLazyItemSlots = mapOf(
        TestItem::class to { _, item -> CountingRow((item as TestItem).label) },
    )
    FiberglassLazyColumn(
        sections = sections,
        headerSlot = { CountingRow(it.title) },
        itemSlots = slots,
        modifier = Modifier.size(300.dp, 400.dp),
        headerKey = headerKey,
        itemKey = itemKey,
    )
}

@Composable
private fun CountingRow(tag: String) {
    var count by rememberSaveable { mutableIntStateOf(0) }
    BasicText(
        text = "$tag:$count",
        modifier = Modifier.height(32.dp).testTag(tag).clickable { count++ },
    )
}

private fun isSaveableValue(value: Any?): Boolean = when (value) {
    null, is String, is Number, is Boolean, is Char -> true
    is MutableState<*> -> isSaveableValue(value.value)
    is List<*> -> value.all(::isSaveableValue)
    is Map<*, *> -> value.all { (key, entry) -> isSaveableValue(key) && isSaveableValue(entry) }
    else -> false
}

private data class TestItem(override val id: Any, val label: String) : FiberglassItem

private data class TestHeader(
    override val id: Any,
    override val title: String,
    val revision: Int = 0,
) : FiberglassStickyHeaderItem

private data class UnsupportedKey(val value: String)
