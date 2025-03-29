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
import androidx.compose.ui.layout.onSizeChanged
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uk.ac.tees.mad.weatherwise.utils.Utils.generateSnowflakes
import kotlin.random.Random

@Composable
fun SnowAnimation() {
    val snowflakes = remember { generateSnowflakes(120) }
    val canvasHeight = remember { mutableFloatStateOf(0f) }
    val dropPositions = remember { mutableStateListOf(*Array(120) { 0f }) }

    LaunchedEffect(Unit) {
        snowflakes.forEachIndexed { index, snowflake ->
            launch {
                while (true) {
                    dropPositions[index] = 0f
                    animate(
                        initialValue = 0f,
                        targetValue = canvasHeight.floatValue,
                        animationSpec = infiniteRepeatable(
                            animation = tween(
                                durationMillis = (Random.nextInt(
                                    3000,
                                    6000
                                )), // Randomize snowflake fall duration
                                easing = LinearEasing
                            ),
                            repeatMode = RepeatMode.Restart
                        )
                    ) { value, _ ->
                        dropPositions[index] = value
                    }
                    delay(Random.nextLong(50, 300)) // Delay between snowflakes falling
                }
            }
        }
    }

    Canvas(modifier = Modifier
        .fillMaxSize()
        .onSizeChanged { size -> canvasHeight.floatValue = size.height.toFloat() }
    ) {
        val canvasWidth = size.width

        for ((index, snowflake) in snowflakes.withIndex()) {
            val xStart = snowflake.x * canvasWidth
            val yStart = dropPositions[index] - snowflake.size

            val xEnd = xStart + (snowflake.drift * 5)

            drawCircle(
                color = Color.White.copy(alpha = snowflake.opacity),
                radius = snowflake.size,
                center = Offset(xEnd, yStart)
            )
        }
    }
}
