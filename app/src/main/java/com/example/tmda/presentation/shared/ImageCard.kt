package com.example.tmda.presentation.shared

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tmda.presentation.movies.getTmdbImageLink
import com.example.tmda.ui.theme.WhiteTransparent20


@Composable
fun ImageCard(imagePath: String, title: String) {
    val mainShape = remember {
        mainShape()
    }
    Surface(
        shape = mainShape,
        modifier = Modifier
            .width(200.dp)
            .height(270.dp)
    ) {

        AsyncImage(
            model = getTmdbImageLink(imagePath) ,
            contentDescription = "$title image",
            modifier = Modifier
                .graphicsLayer { alpha = 0.99f }
                .drawWithContent {
                    val colors = listOf(Color.Black, WhiteTransparent20)
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(colors),
                        blendMode = BlendMode.DstIn
                    )
                }
                .fillMaxSize(),
            contentScale = ContentScale.Crop,

            )

    }
}



