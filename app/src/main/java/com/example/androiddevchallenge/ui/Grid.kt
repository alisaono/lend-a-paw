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
