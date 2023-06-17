@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tmda.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.tmda.R
import com.example.tmda.presentation.movies.getTmdbImageLink
import com.example.tmda.presentation.movies.moviesList.ErrorComponent
import com.example.tmda.presentation.movies.moviesList.MovieUiDto
import com.example.tmda.presentation.navigation.navigateToMovieDetails
import com.example.tmda.presentation.navigation.navigateToTvShowDetailsScreen
import com.example.tmda.presentation.search.data.SearchItemModel
import com.example.tmda.presentation.shared.UiStates.ErrorScreen
import com.example.tmda.presentation.shared.UiStates.LoadingScreen
import com.example.tmda.presentation.shared.mainShape
import com.example.tmda.ui.theme.BlackTransparent28
import com.example.tmda.ui.theme.BlackTransparent37
import com.example.tmda.ui.theme.PineGreenMedium


@Composable
fun SearchScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<SearchViewModel>()
    val movies = viewModel.moviesStateHolder.searchPagingProvider.collectAsLazyPagingItems()
    val series = viewModel.seriesStateHolder.searchPagingProvider.collectAsLazyPagingItems()
    val movieListState = rememberLazyListState()
    val seriesListState = rememberLazyListState()

    LaunchedEffect(key1 = viewModel.moviesStateHolder.displayedKeyWord.value) {
        movieListState.scrollToItem(0, 0)
    }
    LaunchedEffect(key1 = viewModel.seriesStateHolder.displayedKeyWord.value) {
        seriesListState.scrollToItem(0, 0)
    }

    Box(contentAlignment = Alignment.TopCenter) {
        when (viewModel.searchType.value) {
            SearchType.Movie -> {
                CardList(onItemClicked = navController::navigateToMovieDetails, listState = movieListState, searchItems = movies) {
                    movies.retry()
                }
            }

            SearchType.Series -> {
                CardList(
                    onItemClicked = navController::navigateToTvShowDetailsScreen,
                    listState = seriesListState,
                    searchItems = series
                ) { series.retry() }
            }

            SearchType.Actors -> TODO()
        }
        SearchBox(
            currentQuery = viewModel.currentStateHolder.displayedKeyWord.value,
            onQueryChange = viewModel::updateKeyword,
            currentSearchType=viewModel.searchType,
            onChipChanged=viewModel::changeSearchType
            )
    }
}

@Composable
fun SearchBox(
    currentQuery: String,
    onQueryChange: (String) -> Unit,
    currentSearchType:State< SearchType>,
    onChipChanged: (SearchType)->Unit,
    ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .clickable(enabled = false) {})
        SearchBar(
            modifier = Modifier.height(60.dp),
            query = currentQuery,
            onQueryChange = onQueryChange,
            onSearch = {},
            active = false,
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mic),
                    contentDescription = null
                )
            },
            placeholder = {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(text = "  Search..")
                }
            },
            shape = RoundedCornerShape(10.dp),
            onActiveChange = {},
            windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
            colors = SearchBarDefaults.colors(containerColor = BlackTransparent37)
        ) {}
        Spacer(modifier = Modifier.height(24.dp))
        ChipRow(typeState = currentSearchType, onClick = onChipChanged)
    }
}

@Composable
fun ChipRow(typeState: State<SearchType>, onClick: (SearchType) -> Unit) {
    Row {
        ChipItem(
            type = SearchType.Movie,
            isSelected = typeState.value == SearchType.Movie,
            onClick
        )
        Spacer(modifier = Modifier.width(16.dp))
        ChipItem(
            type = SearchType.Series,
            isSelected = typeState.value == SearchType.Series,
            onClick
        )
        Spacer(modifier = Modifier.width(16.dp))
        ChipItem(
            type = SearchType.Actors,
            isSelected = typeState.value == SearchType.Actors,
            onClick
        )


    }
}

@Composable
fun ChipItem(type: SearchType, isSelected: Boolean, onClick: (SearchType) -> Unit) {
    val color = if (isSelected) PineGreenMedium else Color.Transparent
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .clickable { onClick(type) }, contentAlignment = Alignment.Center
    ) {

        Text(
            text = type.title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
    }
}


@Composable
fun CardList(
    onItemClicked: (Int) -> Unit,
    listState: LazyListState,
    searchItems: LazyPagingItems<SearchItemModel>,
    onTryAgain: () -> Unit
) {
    when (searchItems.loadState.refresh) {
        is LoadState.Error -> ErrorScreen(onTryAgain)
        is LoadState.Loading -> LoadingScreen()
        is LoadState.NotLoading -> {
            LazyColumn(
                Modifier.fillMaxSize(),
                state = listState,
            ) {
                item { Spacer(modifier = Modifier.height(200.dp)) }
                items(
                    count = searchItems.itemSnapshotList.size,
                    contentType = { MovieUiDto::class },
                    key = { searchItems.peek(it)!!.id },

                    ) {
                    ItemCard(
                        searchItemModel = searchItems[it]!!,
                        onCardClicked = onItemClicked,
                    )
                }

                when (val state = searchItems.loadState.append) {
                    is LoadState.Error -> item { ErrorComponent(onTryAgain) }

                    is LoadState.Loading -> {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(128.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                LoadingScreen()
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
fun ItemCard(
    searchItemModel: SearchItemModel,
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
            val coroutineScope = rememberCoroutineScope()
            AsyncImage(
                model = getTmdbImageLink(
                    searchItemModel.backdropPath ?: searchItemModel.posterPath
                ),
                contentDescription = searchItemModel.title + "image",
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
                    text = searchItemModel.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "${searchItemModel.releaseDate}. ${searchItemModel.genres} .${searchItemModel.originalLanguage}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = searchItemModel.voteAverage.toString(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                    }
                    Text(
                        text = searchItemModel.voteCount.toString(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )

                }
            }
        }


    }
}


val itemCardShape = mainShape(cornerRadiusDegree = 100f, slopeLength = 30f)