package uk.ac.tees.mad.weatherwise.presentation.ui


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uk.ac.tees.mad.weatherwise.R
import uk.ac.tees.mad.weatherwise.presentation.navigation.Screens

@Composable
fun SplashScreen(navController: NavController) {

    val sunRotation = remember { Animatable(0f) }
    val cloudLeftOffset = remember { Animatable(0f) }
    val cloudRightOffset = remember { Animatable(0f) }
    var isTextVisible by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()

    LaunchedEffect(Unit) {

        launch {
            sunRotation.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 4000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }


        launch {
            cloudLeftOffset.animateTo(
                targetValue = 80f,
                animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
            )
            cloudLeftOffset.animateTo(
                targetValue = 30f,
                animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
            )
        }


        launch {
            cloudRightOffset.animateTo(
                targetValue = -80f,
                animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
            )
            cloudRightOffset.animateTo(
                targetValue = -30f,
                animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
            )
        }

        delay(800)
        isTextVisible = true
        delay(3000)
        navController.navigate(if(auth.currentUser!=null) Screens.MainScreen.route
        else Screens.AuthenticationScreen.route){
            popUpTo(Screens.SplashScreen.route){
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.sun),
            contentDescription = "Sun",
            modifier = Modifier
                .size(150.dp)
                .rotate(sunRotation.value)
        )

        Image(
            painter = painterResource(id = R.drawable.cloud1),
            contentDescription = "Cloud Left",
            modifier = Modifier
                .size(100.dp)
                .offset(x = cloudLeftOffset.value.dp, y = (30).dp)
        )


        Image(
            painter = painterResource(id = R.drawable.cloud2),
            contentDescription = "Cloud Right",
            modifier = Modifier
                .size(100.dp)
                .offset(x = cloudRightOffset.value.dp, y = (30).dp)
        )


        AnimatedVisibility(
            visible = isTextVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 2000))
        ) {
            Text(
                text = "Stay ahead of the weather, wherever you go!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 150.dp)
            )
        }

    }
}
