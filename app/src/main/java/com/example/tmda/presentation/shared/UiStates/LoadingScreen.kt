package com.example.tmda.presentation.shared.UiStates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.tmda.R

@Composable
fun LoadingScreen(modifier: Modifier=Modifier){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.neon_loading))
    val progress by animateLottieCompositionAsState(composition, restartOnPlay = true, iterations = 5)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(modifier=Modifier.fillMaxSize(),
            composition = composition,
            progress = { progress },
        )
    }
}