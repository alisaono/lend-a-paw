/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

@Composable
fun Grid(
    modifier: Modifier = Modifier,
    numColumns: Int = 2,
    content: @Composable() () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val columnWidth = (constraints.maxWidth / numColumns)
        val placeables = measurables.map { measurable ->
            measurable.measure(
                constraints.copy(minHeight = 0, minWidth = 0, maxWidth = columnWidth)
            )
        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            val yPositions = mutableListOf<Int>() // by columns
            repeat(numColumns) { yPositions.add(0) }
            placeables.forEachIndexed { index, placeable ->
                val columnIndex = (index % numColumns)
                placeable.place(x = (columnIndex * columnWidth), y = yPositions[columnIndex])
                yPositions[columnIndex] += placeable.height
            }
        }
    }
}
