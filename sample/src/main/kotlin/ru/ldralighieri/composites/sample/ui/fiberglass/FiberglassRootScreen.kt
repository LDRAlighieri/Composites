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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.ldralighieri.composites.sample.navigation.CompositesFiberglassArgs
import ru.ldralighieri.composites.sample.navigation.CompositesFiberglassColumnRoute
import ru.ldralighieri.composites.sample.navigation.CompositesFiberglassGridRoute
import ru.ldralighieri.composites.sample.navigation.LocalNavigator
import ru.ldralighieri.composites.sample.navigation.Navigator
import ru.ldralighieri.composites.sample.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FiberglassRootScreen(args: CompositesFiberglassArgs) {
    val navigator: Navigator = LocalNavigator.current

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(text = args.title, style = AppTheme.typography.headlineSmall)
            },
            navigationIcon = {
                IconButton(onClick = { navigator.navigateBack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppTheme.colors.surfaceColorAtElevation(3.dp),
                navigationIconContentColor = AppTheme.colors.onSurface,
                titleContentColor = AppTheme.colors.onSurface,
                actionIconContentColor = AppTheme.colors.onSurfaceVariant,
            ),
        )

        FiberglassScreenItem(
            title = "Column",
            onClick = {
                navigator.navigateTo(
                    CompositesFiberglassColumnRoute.create(title = "Fiberglass column"),
                )
            },
        )

        FiberglassScreenItem(
            title = "Grid",
            onClick = {
                navigator.navigateTo(
                    CompositesFiberglassGridRoute.create(title = "Fiberglass grid"),
                )
            },
        )
    }
}

@Composable
private fun FiberglassScreenItem(
    title: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = AppTheme.dimensions.horizontalGuideline, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = title,
            color = AppTheme.colors.onBackground,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = AppTheme.typography.titleLarge,
        )
    }
}
