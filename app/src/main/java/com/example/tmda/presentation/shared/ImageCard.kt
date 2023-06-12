package com.example.tmda.presentation.shared

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tmda.R

//@Preview(showBackground = false, showSystemUi = false)

@Composable
fun ImageCard(imageUrl: String?) {
    val mainShape = remember { mainShape() }
    Surface(
        shape = mainShape,
        modifier = Modifier
            .width(200.dp)
            .height(270.dp), color = Color.Black
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Image Card",
        )
    }
}



