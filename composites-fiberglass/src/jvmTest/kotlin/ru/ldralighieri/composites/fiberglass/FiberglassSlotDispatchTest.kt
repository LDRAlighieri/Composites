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

package ru.ldralighieri.composites.fiberglass

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ru.ldralighieri.composites.fiberglass.column.FiberglassColumn
import ru.ldralighieri.composites.fiberglass.column.FiberglassFlowColumn
import ru.ldralighieri.composites.fiberglass.column.FiberglassLazyColumn
import ru.ldralighieri.composites.fiberglass.grid.horizontal.FiberglassLazyHorizontalGrid
import ru.ldralighieri.composites.fiberglass.grid.horizontal.FiberglassLazyHorizontalStaggeredGrid
import ru.ldralighieri.composites.fiberglass.grid.vertical.FiberglassLazyVerticalGrid
import ru.ldralighieri.composites.fiberglass.grid.vertical.FiberglassLazyVerticalStaggeredGrid
import ru.ldralighieri.composites.fiberglass.model.FiberglassColumnItemSlots
import ru.ldralighieri.composites.fiberglass.model.FiberglassItem
import ru.ldralighieri.composites.fiberglass.model.FiberglassLazyGridItemSlots
import ru.ldralighieri.composites.fiberglass.model.FiberglassLazyItemSlots
import ru.ldralighieri.composites.fiberglass.model.FiberglassLazyStaggeredGridItemSlots
import ru.ldralighieri.composites.fiberglass.model.FiberglassRowItemSlots
import ru.ldralighieri.composites.fiberglass.model.FiberglassStickyHeaderItem
import ru.ldralighieri.composites.fiberglass.row.FiberglassFlowRow
import ru.ldralighieri.composites.fiberglass.row.FiberglassLazyRow
import ru.ldralighieri.composites.fiberglass.row.FiberglassRow
import kotlin.reflect.KClass
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertFailsWith

@OptIn(ExperimentalTestApi::class)
@RunWith(Parameterized::class)
internal class FiberglassSlotDispatchTest(private val layout: TestLayout) {
    @Test
    fun exactRegistrationReceivesTheItemAndItsPosition() = runComposeUiTest {
        setContent {
            layout.Content(listOf(BaseItem("first"), BaseItem("second")), listOf(BaseItem::class))
        }

        onNodeWithTag("first").assertTextEquals("BaseItem:first:0")
        onNodeWithTag("second").assertTextEquals("BaseItem:second:1")
    }

    @Test
    fun unknownTypeReportsItsClassAndPosition() {
        assertMissingSlot(listOf(BaseItem("first"), UnknownItem("unknown")), listOf(BaseItem::class), UnknownItem::class, 1)
    }

    @Test
    fun emptyRegistryReportsMissingSlot() {
        assertMissingSlot(listOf(BaseItem("first")), emptyList(), BaseItem::class, 0)
    }

    @Test
    fun superclassRegistrationDoesNotHandleSubclass() {
        assertMissingSlot(listOf(ChildItem("child")), listOf(BaseItem::class), ChildItem::class, 0)
    }

    @Test
    fun interfaceRegistrationDoesNotHandleImplementation() {
        assertMissingSlot(listOf(BaseItem("first")), listOf(FiberglassItem::class), BaseItem::class, 0)
    }

    @Test
    fun exactSubclassRegistrationWinsRegardlessOfRegistryOrder() = runComposeUiTest {
        var registeredTypes by mutableStateOf(listOf(FiberglassItem::class, BaseItem::class, ChildItem::class))
        setContent { layout.Content(listOf(ChildItem("child")), registeredTypes) }

        onNodeWithTag("child").assertTextEquals("ChildItem:child:0")
        runOnIdle { registeredTypes = registeredTypes.reversed() }
        onNodeWithTag("child").assertTextEquals("ChildItem:child:0")
    }

    @Test
    fun emptyItemsDoNotRequireSlots() = runComposeUiTest {
        setContent { layout.Content(emptyList(), emptyList()) }
        waitForIdle()
    }

    private fun assertMissingSlot(
        items: List<FiberglassItem>,
        registeredTypes: List<KClass<out FiberglassItem>>,
        missingType: KClass<out FiberglassItem>,
        position: Int,
    ) {
        val exception = assertFailsWith<IllegalArgumentException> {
            runComposeUiTest {
                setContent { layout.Content(items, registeredTypes) }
                waitForIdle()
            }
        }
        val message = exception.message.orEmpty()
        assertContains(message, missingType.toString())
        assertContains(message, "position $position")
        assertContains(message, "exact runtime class")
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun layouts(): List<TestLayout> = TestLayout.entries
    }
}

internal enum class TestLayout {
    Column,
    Row,
    FlowColumn,
    FlowRow,
    LazyColumn,
    LazyRow,
    Sections,
    VerticalGrid,
    HorizontalGrid,
    VerticalStaggeredGrid,
    HorizontalStaggeredGrid,
    ;

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Content(items: List<FiberglassItem>, registeredTypes: List<KClass<out FiberglassItem>>) {
        val columnSlots: FiberglassColumnItemSlots = registeredTypes.associateWith { type ->
            { position, item -> SlotText(type, position, item) }
        }
        val rowSlots: FiberglassRowItemSlots = registeredTypes.associateWith { type ->
            { position, item -> SlotText(type, position, item) }
        }
        val lazySlots: FiberglassLazyItemSlots = registeredTypes.associateWith { type ->
            { position, item -> SlotText(type, position, item) }
        }
        val gridSlots: FiberglassLazyGridItemSlots = registeredTypes.associateWith { type ->
            { position, item -> SlotText(type, position, item) }
        }
        val staggeredSlots: FiberglassLazyStaggeredGridItemSlots = registeredTypes.associateWith { type ->
            { position, item -> SlotText(type, position, item) }
        }
        val modifier = Modifier.size(300.dp)
        when (this) {
            Column -> FiberglassColumn(items, columnSlots, modifier)

            Row -> FiberglassRow(items, rowSlots, modifier)

            FlowColumn -> FiberglassFlowColumn(items, columnSlots, modifier)

            FlowRow -> FiberglassFlowRow(items, rowSlots, modifier)

            LazyColumn -> FiberglassLazyColumn(items, lazySlots, modifier)

            LazyRow -> FiberglassLazyRow(items, lazySlots, modifier)

            Sections -> FiberglassLazyColumn(
                sections = mapOf(TestHeader to items),
                headerSlot = { BasicText(it.title) },
                itemSlots = lazySlots,
                modifier = modifier,
            )

            VerticalGrid -> FiberglassLazyVerticalGrid(items, gridSlots, GridCells.Fixed(1), modifier)

            HorizontalGrid -> FiberglassLazyHorizontalGrid(items, gridSlots, GridCells.Fixed(1), modifier)

            VerticalStaggeredGrid -> FiberglassLazyVerticalStaggeredGrid(items, staggeredSlots, StaggeredGridCells.Fixed(1), modifier)

            HorizontalStaggeredGrid -> FiberglassLazyHorizontalStaggeredGrid(items, staggeredSlots, StaggeredGridCells.Fixed(1), modifier)
        }
    }
}

@Composable
private fun SlotText(type: KClass<out FiberglassItem>, position: Int, item: FiberglassItem) {
    BasicText("${type.simpleName}:${item.id}:$position", Modifier.size(100.dp, 40.dp).testTag(item.id as String))
}

private open class BaseItem(override val id: String) : FiberglassItem

private class ChildItem(id: String) : BaseItem(id)

private class UnknownItem(override val id: String) : FiberglassItem

private data object TestHeader : FiberglassStickyHeaderItem {
    override val id: String = "header"
    override val title: String = "Header"
}
