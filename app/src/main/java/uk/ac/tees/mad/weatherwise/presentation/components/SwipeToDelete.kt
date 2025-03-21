package uk.ac.tees.mad.weatherwise.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDelete(
    item: T,
    onDelete: (T) -> Unit,
    content: @Composable () -> Unit
) {
    var isDismissed by remember { mutableStateOf(false) }
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.StartToEnd || it == SwipeToDismissBoxValue.EndToStart) {
                isDismissed = true
                true
            } else {
                false
            }
        }
    )

    val alpha by animateFloatAsState(targetValue = if (isDismissed) 0f else 1f, label = "Alpha Animation")

    LaunchedEffect(isDismissed) {
        if (isDismissed) {
            delay(300)
            onDelete(item)
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {},
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alpha)
        ) {
            content()
        }
    }
}