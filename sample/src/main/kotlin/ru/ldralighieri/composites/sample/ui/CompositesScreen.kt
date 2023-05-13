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

package ru.ldralighieri.composites.sample.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.ldralighieri.composites.sample.R
import ru.ldralighieri.composites.sample.ThemePreviews
import ru.ldralighieri.composites.sample.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompositesScreen(onFiberglassClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Composites Materials Catalog",
                    style = AppTheme.typography.headlineSmall
                )
            },
            colors = topAppBarColors(
                containerColor = AppTheme.colors.surfaceColorAtElevation(3.dp),
                titleContentColor = AppTheme.colors.onSurface,
                actionIconContentColor = AppTheme.colors.onSurfaceVariant
            )
        )

        CompositesContent(onFiberglassClick)
    }
}

@Composable
private fun CompositesContent(onFiberglassClick: () -> Unit) {
    LazyColumn {
        item(key = "fiberglass") {
            CompositeItem(title = "Fiberglass", onClick = onFiberglassClick)
        }
    }
}

@Composable
private fun CompositeItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = AppTheme.dimensions.horizontalGuideline, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.fiberglass),
            contentDescription = "",
            modifier = Modifier.size(48.dp),
            colorFilter = ColorFilter.tint(color = AppTheme.colors.onBackground)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            color = AppTheme.colors.onBackground,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = AppTheme.typography.titleLarge
        )
    }
}

@Composable
@ThemePreviews
private fun CompositeItemPreview() {
    AppTheme(dynamicColor = false) {
        CompositeItem(title = "Fiberglass", onClick = {})
    }
}
