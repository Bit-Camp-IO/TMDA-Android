package com.example.tmda.presentation.shared.base.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tmda.presentation.shared.HomeCardModifier
import com.example.tmda.presentation.shared.base.TotalRatingIcons

@Composable
fun BaseHomeCard(
    id: Int,
    title: String,
    date: String,
    genres: String,
    voteAverage: Double,
    posterPath: String?,
    backdropPath: String?,
    onClick: (Int) -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = HomeCardModifier
            .clickable { onClick(id) }
    ) {
        content()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(170.dp)
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
            Text(
                text = "$date.$genres",
                style = MaterialTheme.typography.labelSmall
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = voteAverage.toString(),
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.width(4.dp))
                TotalRatingIcons(rating = voteAverage.toFloat(), iconSize = 16.dp)
            }
        }

    }

}