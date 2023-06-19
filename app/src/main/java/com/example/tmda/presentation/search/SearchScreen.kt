@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tmda.presentation.search

import androidx.activity.addCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tmda.R
import com.example.tmda.presentation.navigation.Destinations
import com.example.tmda.presentation.navigation.navigateToMovieDetails
import com.example.tmda.presentation.navigation.navigateToSearchPersonScreen
import com.example.tmda.presentation.navigation.navigateToTvShowDetailsScreen
import com.example.tmda.presentation.search.components.ActorCardList
import com.example.tmda.presentation.search.components.CardList
import com.example.tmda.ui.theme.BlackTransparent37
import com.example.tmda.ui.theme.PineGreenMedium


@Composable
fun SearchScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<SearchViewModel>()
    val movies = viewModel.moviesStateHolder.searchPagingProvider.collectAsLazyPagingItems()
    val series = viewModel.seriesStateHolder.searchPagingProvider.collectAsLazyPagingItems()
    val actors = viewModel.actorStateHolder.searchPagingProvider.collectAsLazyPagingItems()


    Box(contentAlignment = Alignment.TopCenter) {
        when (viewModel.searchType.value) {
            SearchType.Movie -> {
                CardList(
                    onItemClicked = navController::navigateToMovieDetails,
                    listState = viewModel.moviesStateHolder.listState,
                    searchItems = movies
                ) {
                    movies.retry()
                }
            }

            SearchType.Series -> {
                CardList(
                    onItemClicked = navController::navigateToTvShowDetailsScreen,
                    listState = viewModel.seriesStateHolder.listState,
                    searchItems = series
                ) { series.retry() }
            }

            SearchType.Actors -> ActorCardList(
                onItemClicked = navController::navigateToSearchPersonScreen,
                listState = viewModel.actorStateHolder.listState,
                searchItems = actors
            ) {
                actors.retry()
            }
        }
        SearchBox(
            currentQuery = viewModel.currentStateHolder.displayedKeyWord.value,
            onQueryChange = viewModel::updateKeyword,
            currentSearchType = viewModel.searchType,
            onChipChanged = viewModel::changeSearchType
        )
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    SideEffect {

        onBackPressedDispatcher?.addCallback(owner = lifecycleOwner) {
            val currentStateHolder = viewModel.currentStateHolder
            if (currentStateHolder.displayedKeyWord.value.isNotEmpty()) {
                currentStateHolder.updateKeyword("")
                return@addCallback
            }

            viewModel.moviesStateHolder.updateKeyword("")
            viewModel.seriesStateHolder.updateKeyword("")
            viewModel.actorStateHolder.updateKeyword("")
            navController.navigate(Destinations.MOVIES_ROUTE) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}

@Composable
fun SearchBox(
    currentQuery: String,
    onQueryChange: (String) -> Unit,
    currentSearchType: State<SearchType>,
    onChipChanged: (SearchType) -> Unit,
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
