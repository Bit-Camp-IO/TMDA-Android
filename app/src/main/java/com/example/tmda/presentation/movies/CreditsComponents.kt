package com.example.tmda.presentation.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movies.domain.enities.credits.CastMember
import com.example.movies.domain.enities.credits.CreditItem
import com.example.movies.domain.enities.credits.Credits
import com.example.movies.domain.enities.credits.CrewMember
import com.example.tmda.presentation.shared.LoadingScreen
import com.example.tmda.presentation.shared.UiState
import com.example.tmda.presentation.shared.mainShape
import com.example.tmda.ui.theme.BlackTransparent60
import com.example.tmda.ui.theme.PineGreen
import com.example.tmda.ui.theme.WhiteTransparent60

@Composable
fun CreditsComponent(
    title: String = "Casts",
    creditItemsState: UiState<Credits>,
    onSeeAllClicked: () -> Unit,
    onCardClicked: (Int) -> Unit
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Divider(
                modifier = Modifier
                    .height(20.dp)
                    .width(5.dp), thickness = 1.dp, color = PineGreen
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        }
        TextButton(onClick = { /*TODO*/ }, contentPadding = PaddingValues(0.dp)) {
            Text(
                text = "See All",
                color = PineGreen,
                style = MaterialTheme.typography.titleSmall
            )

        }
    }

    when (creditItemsState) {
        is UiState.Failure -> TODO()
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Success -> {
            val creditItems = creditItemsState.data
            LazyRow {
                item { Spacer(modifier = Modifier.width(16.dp)) }
                items(creditItems.cast .size, key = { creditItems.cast[it].id }) {
                    CreditItemsCard(creditItems.cast[it])
                }
                item { Spacer(modifier = Modifier.width(16.dp)) }

            }
        }
    }


    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 32.dp)
    )
}


@Composable
fun CreditItemsCard(creditItem: CreditItem) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(180.dp)
            .clip(mainShape()),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = getTmdbImageLink(creditItem.profilePath),
            contentDescription = "${creditItem.name} image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(color = BlackTransparent60)
                .padding(8.dp)
        ) {
            val subTitleText = when (val creditItem = creditItem) {
                is CastMember -> creditItem.character
                is CrewMember -> creditItem.job
                else -> TODO("Implement Behavior for the new Type")
            }
            Text(
                text = creditItem.name,
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Visible
            )
            Text(
                text = subTitleText,
                fontSize = 10.sp,
                color = WhiteTransparent60,
                maxLines = 1,
                overflow = TextOverflow.Visible
            )

        }
    }

}