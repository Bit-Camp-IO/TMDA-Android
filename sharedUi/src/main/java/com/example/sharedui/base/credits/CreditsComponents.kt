package com.example.sharedui.base.credits

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sharedComponent.entities.credits.CastMember
import com.example.sharedComponent.entities.credits.CreditItem
import com.example.sharedComponent.entities.credits.CrewMember
import com.example.sharedui.x.DividerTitle
import com.example.sharedui.x.NoDataComponent
import com.example.sharedui.base.mainShape
import com.example.sharedui.colors.BlackTransparent60
import com.example.sharedui.colors.WhiteTransparent60
import com.example.sharedui.uiStates.LoadingScreen
import com.example.sharedui.uiStates.UiState
import com.example.sharedui.utils.getTmdbImageLink

@Composable
fun CreditsComponent(
    title: String = "Casts",
    creditItemsState: UiState<List<CastMember>>,
    onCardClicked: (Int) -> Unit
) {


    DividerTitle(title = title)


    when (creditItemsState) {
        is UiState.Failure -> {}
        is UiState.Loading -> LoadingScreen()


        is UiState.Success -> {
            val castMembers = creditItemsState.data
            LazyRow {
                item { Spacer(modifier = Modifier.width(16.dp)) }
                if (castMembers.isEmpty())
                    item {
                        NoDataComponent(
                            modifier = Modifier
                                .height(160.dp)
                        )
                    } else
                    items(castMembers.size, key = { castMembers[it].id }) {
                        CreditItemsCard(castMembers[it], onCardClicked)
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
fun CreditItemsCard(creditItem: CreditItem, onCardClicked: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(180.dp)
            .clip(mainShape)
            .clickable { onCardClicked(creditItem.id) },
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
