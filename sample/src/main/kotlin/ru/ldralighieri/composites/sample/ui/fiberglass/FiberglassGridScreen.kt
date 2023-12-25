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

package ru.ldralighieri.composites.sample.ui.fiberglass

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.ldralighieri.composites.fiberglass.grid.vertical.FiberglassLazyVerticalGrid
import ru.ldralighieri.composites.fiberglass.model.FiberglassItem
import ru.ldralighieri.composites.sample.navigation.CompositesFiberglassGridArgs
import ru.ldralighieri.composites.sample.navigation.LocalNavigator
import ru.ldralighieri.composites.sample.navigation.Navigator
import ru.ldralighieri.composites.sample.theme.AppTheme
import ru.ldralighieri.composites.sample.ui.fiberglass.items.ErrorGridItem
import ru.ldralighieri.composites.sample.ui.fiberglass.items.PrimaryGridItem
import ru.ldralighieri.composites.sample.ui.fiberglass.items.errorGridSlot
import ru.ldralighieri.composites.sample.ui.fiberglass.items.primaryGridSlot

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiberglassGridScreen(args: CompositesFiberglassGridArgs) {
    val navigator: Navigator = LocalNavigator.current

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(text = args.title, style = AppTheme.typography.headlineSmall)
            },
            navigationIcon = {
                IconButton(onClick = { navigator.navigateBack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppTheme.colors.surfaceColorAtElevation(3.dp),
                navigationIconContentColor = AppTheme.colors.onSurface,
                titleContentColor = AppTheme.colors.onSurface,
                actionIconContentColor = AppTheme.colors.onSurfaceVariant
            )
        )

        FiberglassGridContent()
    }
}

@Composable
private fun FiberglassGridContent() {
    val items: List<FiberglassItem> = remember {
        buildList {
            repeat(33) {
                add(if (it % 2 == 0) PrimaryGridItem() else ErrorGridItem())
            }
        }
    }

    FiberglassLazyVerticalGrid(
        items = items,
        itemSlots = mapOf(
            PrimaryGridItem::class to primaryGridSlot(),
            ErrorGridItem::class to errorGridSlot()
        ),
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = WindowInsets.navigationBars
                .only(WindowInsetsSides.Bottom)
                .asPaddingValues()
                .calculateBottomPadding() + AppTheme.dimensions.bottomGuideline
        )
    )
}
