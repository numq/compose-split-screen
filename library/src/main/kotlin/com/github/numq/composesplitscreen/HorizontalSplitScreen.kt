package com.github.numq.composesplitscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun HorizontalSplitScreen(
    modifier: Modifier = Modifier,
    sliderThickness: Dp = 12.dp,
    sliderColor: Color = Color.White,
    initialSliderPercentage: Float = .5f,
    minSliderPercentage: Float = 0f,
    maxSliderPercentage: Float = 1f,
    indicator: @Composable () -> Unit = { Icon(Icons.Default.DragIndicator, null) },
    left: @Composable (Dp) -> Unit,
    right: @Composable (Dp) -> Unit,
) {
    require(
        minSliderPercentage in 0f..1f && maxSliderPercentage in 0f..1f && minSliderPercentage < maxSliderPercentage && initialSliderPercentage in minSliderPercentage..maxSliderPercentage
    ) { "Invalid percentage values" }

    BoxWithConstraints(modifier = modifier) {
        val containerWidth = maxWidth

        var offsetPercent by remember { mutableStateOf(initialSliderPercentage) }

        val offsetX by remember(sliderThickness, containerWidth, offsetPercent) {
            derivedStateOf {
                offsetPercent.times(containerWidth - sliderThickness).coerceIn(0.dp, containerWidth - sliderThickness)
            }
        }

        BoxWithConstraints(
            modifier = Modifier.width(offsetX + sliderThickness / 2).fillMaxHeight(), contentAlignment = Alignment.Center
        ) {
            left(maxWidth)
        }
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize().padding(start = offsetX + sliderThickness / 2),
            contentAlignment = Alignment.Center
        ) {
            right(maxWidth)
        }
        Box(
            modifier = Modifier.width(sliderThickness).fillMaxHeight().offset(x = offsetX).background(sliderColor)
                .pointerInput(sliderThickness, minSliderPercentage, maxSliderPercentage, containerWidth) {
                    detectDragGestures { _, dragAmount ->
                        val newOffsetX =
                            (offsetX + dragAmount.x.toDp()).coerceIn(0.dp, containerWidth - sliderThickness)

                        offsetPercent = (newOffsetX / (containerWidth - sliderThickness)).coerceIn(
                            minSliderPercentage, maxSliderPercentage
                        )
                    }
                }, contentAlignment = Alignment.Center
        ) {
            indicator()
        }
    }
}