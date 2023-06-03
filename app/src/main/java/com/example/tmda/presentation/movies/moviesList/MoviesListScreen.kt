package com.example.tmda.presentation.movies.moviesList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tmda.presentation.movies.MovieCard
import com.example.tmda.presentation.shared.AppToolBar

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun MoviesListScreen() {
    val viewModel = hiltViewModel<MoviesListViewModel>()
    val movies = viewModel.getMovieList().collectAsLazyPagingItems()

    LazyColumn(
        Modifier
            .background(Color.Transparent)
            .fillMaxSize()

    ) {

        stickyHeader {
            Spacer(modifier = Modifier.height(16.dp))
            MovieListScreenAppBar()
        }
        items(count = movies.itemCount) { MovieCard(movie = movies[it]!!) }
        when (val state = movies.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                //TODO Error Item
                //state.error to get error message
            }
            is LoadState.Loading -> { // Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "Refresh Loading"
                        )

                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }
            else -> {}
        }
        when (val state = movies.loadState.append) { // Pagination
            is LoadState.Error -> {

            }
            is LoadState.Loading -> { // Pagination Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "Pagination Loading")

                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }
            else -> {}
        }
    }


}


@Composable
fun MovieListScreenAppBar() {
    AppToolBar(title = "Top Movies") {}
}

