package com.example.sharedui.base.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sharedui.base.TotalRatingIcons
import com.example.sharedui.colors.PineGreenDark
import com.example.sharedui.colors.WhiteTransparent60
import com.example.sharedui.utils.getTmdbImageLink

@Composable
fun BaseNowPlayingCard(
    title: String,
    id: Int,
    posterPath: String?,
    voteAverage: Double,

    voteCount: Int,
    onClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .clickable { onClick(id) },
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            model = getTmdbImageLink(posterPath),
            contentDescription = title + "image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "Marvel Studios",
                style = MaterialTheme.typography.bodySmall,
                color = PineGreenDark
            )
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            TotalRatingIcons(rating = voteAverage.toFloat(), iconSize = 24.dp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "From $voteCount users",
                style = MaterialTheme.typography.bodySmall,
                color = WhiteTransparent60
            )

        }
    }
}