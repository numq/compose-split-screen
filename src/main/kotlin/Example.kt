import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.singleWindowApplication

fun main() = singleWindowApplication(title = "Split Screen") {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalSplitScreen(
            modifier = Modifier.weight(1f),
            left = { dp ->
                Box(
                    modifier = Modifier.fillMaxSize().background(color = Color.Green),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(dp.value.toString())
                }
            },
            right = { dp ->
                Box(
                    modifier = Modifier.fillMaxSize().background(color = Color.Blue),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(dp.value.toString())
                }
            }
        )
        VerticalSplitScreen(
            modifier = Modifier.weight(1f),
            top = { dp ->
                Box(
                    modifier = Modifier.fillMaxSize().background(color = Color.Yellow),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(dp.value.toString())
                }
            },
            bottom = { dp ->
                Box(
                    modifier = Modifier.fillMaxSize().background(color = Color.Red),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(dp.value.toString())
                }
            }
        )
    }
}