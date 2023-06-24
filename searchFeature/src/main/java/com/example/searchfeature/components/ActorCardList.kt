package com.example.searchfeature.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.example.sharedComponent.entities.people.Person
import com.example.sharedComponent.entities.people.PersonDetails
import com.example.sharedui.colors.BlackTransparent28
import com.example.sharedui.utils.getTmdbImageLink

@Composable
fun ActorCardList(
    onItemClicked: (Int) -> Unit,
    listState: LazyListState,
    searchItems: LazyPagingItems<Person>,
    onTryAgain: () -> Unit
) {
    when (searchItems.loadState.refresh) {
        is LoadState.Error -> com.example.sharedui.uiStates.ErrorScreen(onTryAgain)
        is LoadState.Loading -> com.example.sharedui.uiStates.LoadingScreen()
        is LoadState.NotLoading -> {
            LazyColumn(
                Modifier.fillMaxSize(),
                state = listState,
            ) {
                item { Spacer(modifier = Modifier.height(200.dp)) }
                items(
                    count = searchItems.itemSnapshotList.size,
                    contentType = { PersonDetails::class },
                    key = { searchItems.peek(it)!!.id },

                    ) {
                    ActorItemCard(
                        searchItemModel = searchItems[it]!!,
                        onCardClicked = onItemClicked,
                    )
                }

                when (searchItems.loadState.append) {
                    is LoadState.Error -> item {
                        com.example.sharedui.uiStates.ErrorComponent(
                            onTryAgain
                        )
                    }

                    is LoadState.Loading -> {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(128.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                com.example.sharedui.uiStates.LoadingScreen()
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }

}

@Composable
fun ActorItemCard(
    searchItemModel: Person,
    onCardClicked: (Int) -> Unit,
) {
    Surface(
        shape = itemCardShape, color = BlackTransparent28,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(vertical = 8.dp)
            .clickable { onCardClicked(searchItemModel.id) }

    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {
            rememberCoroutineScope()
            AsyncImage(
                model = getTmdbImageLink(
                    searchItemModel.profilePath
                ),
                contentDescription = searchItemModel.name + "image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(144.dp)
                    .clip(itemCardShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
                    .padding(end = 16.dp),
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = searchItemModel.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "${searchItemModel.knownForDepartment} . ${searchItemModel.popularity}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))

            }
        }


    }
}

