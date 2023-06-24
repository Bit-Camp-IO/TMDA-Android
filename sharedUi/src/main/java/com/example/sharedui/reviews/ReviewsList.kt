package com.example.sharedui.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharedComponent.entities.review.Review
import com.example.sharedui.x.DividerTitle
import com.example.sharedui.x.NoDataComponent
import com.example.sharedui.uiStates.ErrorComponent
import com.example.sharedui.uiStates.LoadingScreen
import com.example.sharedui.uiStates.UiState

@Composable
fun ReviewsList(reviews: UiState<List<Review>>) {

    Column {
        Row {

            DividerTitle(title = "User Reviews")

        }
        when (reviews) {
            is UiState.Failure -> ErrorComponent {}
            is UiState.Loading -> LoadingScreen(Modifier.height(200.dp))
            is UiState.Success -> {
                val data = reviews.data
                if (data.isEmpty())
                    NoDataComponent(modifier = Modifier.height(200.dp))
                LazyRow {
                    item { Spacer(modifier = Modifier.width(8.dp)) }
                    items(count = data.size, contentType = { Review::class }) {
                        ReviewCard(review = data[it])
                    }
                    item { Spacer(modifier = Modifier.width(8.dp)) }
                }
            }
        }

    }

}

@Composable
fun ReviewCard(review: Review) {
    Column(
        modifier = Modifier
            .size(260.dp, 120.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black)
            .padding(10.dp)


    ) {
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = review.author, fontSize = 10.sp)
        Text(text = review.updatedAt.take(7), fontSize = 6.sp)
        Text(
            text = review.content,
            fontSize = 8.sp,
            lineHeight = 8.sp,
            overflow = TextOverflow.Ellipsis
        )

    }
}