package uk.ac.tees.mad.weatherwise.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uk.ac.tees.mad.weatherwise.utils.Utils.generateRaindrops
import kotlin.random.Random

@Composable
fun RainAnimation() {
    val rainDrops = remember { generateRaindrops(120) } // More raindrops for realism
    val canvasHeight = remember { mutableFloatStateOf(0f) }
    val dropPositions = remember { mutableStateListOf(*Array(120) { 0f }) }

    LaunchedEffect(Unit) {
        rainDrops.forEachIndexed { index, raindrop ->
            launch {
                while (true) {
                    dropPositions[index] = 0f
                    animate(
                        initialValue = 0f,
                        targetValue = canvasHeight.floatValue,
                        animationSpec = infiniteRepeatable(
                            animation = tween(
                                durationMillis = raindrop.speed,
                                easing = LinearEasing
                            ),
                            repeatMode = RepeatMode.Restart
                        )
                    ) { value, _ ->
                        dropPositions[index] = value
                    }
                    delay(Random.nextLong(50, 300))
                }
            }
        }
    }

    Canvas(modifier = Modifier
        .fillMaxSize()
        .onSizeChanged { size -> canvasHeight.floatValue = size.height.toFloat() }
    ) {
        val canvasWidth = size.width

        for ((index, raindrop) in rainDrops.withIndex()) {
            val xStart = raindrop.x * canvasWidth
            val yStart = dropPositions[index] - raindrop.length
            val yEnd = dropPositions[index]

            val xEnd = xStart + (raindrop.slant * 5)

            drawLine(
                color = Color.White.copy(alpha = raindrop.opacity),
                start = Offset(xStart, yStart),
                end = Offset(xEnd, yEnd),
                strokeWidth = raindrop.width,
                cap = StrokeCap.Round
            )
        }
    }
}
