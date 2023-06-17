package com.example.tmda.presentation.shared.base

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.example.tmda.presentation.movies.getTmdbImageLink
import com.example.tmda.presentation.shared.mainShape
import com.example.tmda.ui.theme.WhiteTransparent20

val mainShape = mainShape()

@Composable
fun BaseImageCard(imagePath: String?, title: String, modifier: Modifier) {
        AsyncImage(
            model = getTmdbImageLink(imagePath),
            contentDescription = "$title image",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
}


fun Modifier.imageCardModifier(width: Dp, height:Dp) = this
    .graphicsLayer { alpha = 0.99f }
    .drawWithContent {
        val colors = listOf(Color.Black, WhiteTransparent20)
        drawContent()
        drawRect(
            brush = Brush.verticalGradient(colors),
            blendMode = BlendMode.DstIn
        )
    }.size(width,height)