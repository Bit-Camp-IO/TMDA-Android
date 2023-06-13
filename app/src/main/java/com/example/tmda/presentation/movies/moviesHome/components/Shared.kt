package com.example.tmda.presentation.movies.moviesHome.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tmda.R
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun TotalRatingIcons(
    maxRating: Int = 10,
    rating: Float,
    starsCount: Int = 5,
    iconSize: Dp = 12.dp
) {
    val actualRating = rating * starsCount / maxRating
    val filledStars = floor(actualRating).toInt()
    val unFilledStars = starsCount - ceil(actualRating).toInt()
    val halfFilledStar = starsCount - (filledStars + unFilledStars)
    val sharedModifier = Modifier
        .size(iconSize)
        .padding(end = 8.dp)
        .size(iconSize)

    Row {
        for (i in 0 until filledStars) {
            Icon(
                modifier = sharedModifier,
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        if (halfFilledStar == 1) {
            Icon(
                modifier = sharedModifier,
                painter = painterResource(id = R.drawable.ic_half_star),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        for (i in 0 until unFilledStars) {
            Icon(
                modifier = sharedModifier,
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = Color.White
            )
        }
    }

}