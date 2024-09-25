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
fun VerticalSplitScreen(
    modifier: Modifier = Modifier,
    sliderThickness: Dp = 12.dp,
    sliderColor: Color = Color.White,
    initialPercentage: Float = .5f,
    top: @Composable (Dp) -> Unit,
    bottom: @Composable (Dp) -> Unit,
) {
    BoxWithConstraints(modifier = modifier) {
        val containerHeight = maxHeight

        var offsetPercent by remember { mutableStateOf(initialPercentage) }

        val offsetY by remember(sliderThickness, containerHeight, offsetPercent) {
            derivedStateOf {
                offsetPercent.times(containerHeight - sliderThickness).coerceIn(0.dp, containerHeight - sliderThickness)
            }
        }

        BoxWithConstraints(
            modifier = Modifier.height(offsetY + sliderThickness / 2),
            contentAlignment = Alignment.Center
        ) {
            top(maxHeight)
        }
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize().padding(top = offsetY + sliderThickness / 2),
            contentAlignment = Alignment.Center
        ) {
            bottom(maxHeight)
        }
        Box(
            modifier = Modifier.height(sliderThickness).fillMaxWidth().offset(y = offsetY).background(sliderColor)
                .pointerInput(sliderThickness, containerHeight) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()

                        val newOffsetY =
                            (offsetY + dragAmount.y.toDp()).coerceIn(0.dp, containerHeight - sliderThickness)

                        offsetPercent = (newOffsetY / (containerHeight - sliderThickness)).coerceIn(0f, 1f)
                    }
                }, contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.DragIndicator, null)
        }
    }
}