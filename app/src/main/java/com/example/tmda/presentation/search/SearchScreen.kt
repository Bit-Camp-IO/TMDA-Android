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
import com.example.movies.domain.enities.movie.Movie
import com.example.tmda.R
import com.example.tmda.presentation.movies.getTmdbImageLink
import com.example.tmda.presentation.movies.moviesList.MovieUiDto
import com.example.tmda.presentation.navigation.navigateToMovieDetails
import com.example.tmda.presentation.shared.LoadingScreen
import com.example.tmda.presentation.shared.mainShape
import com.example.tmda.ui.theme.BlackTransparent28
import com.example.tmda.ui.theme.BlackTransparent37
import com.example.tmda.ui.theme.PineGreenMedium


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<SearchViewModel>()
    val movies = viewModel.pageStream.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = viewModel.currentKeyword.value) {
        listState.scrollToItem(0, 0)
    }

    Box(contentAlignment = Alignment.TopCenter) {
        MovieList(
            navController = navController,
            listState = listState,
            movies = movies,
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .clickable(enabled = false) {})
            SearchBar(
                modifier = Modifier.height(60.dp),
                query = viewModel.currentKeyword.value,
                onQueryChange = viewModel::updateKeyword,
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
            ChipRow(typeState = viewModel.searchType, onClick = viewModel::changeSearchType)
        }


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
    Box(modifier = Modifier.clip(RoundedCornerShape(20.dp))
        .background(color)
        .clickable { onClick(type) }, contentAlignment = Alignment.Center) {

        Text(
            text = type.title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
    }
}

@Composable
fun MovieList(
    navController: NavController,
    listState: LazyListState,
    movies: LazyPagingItems<Movie>,
) {


    LazyColumn(
        Modifier
            .background(Color.Transparent)
            .fillMaxSize(),
        state = listState
    ) {

        item { Spacer(modifier = Modifier.height(128.dp)) }
        items(
            count = movies.itemSnapshotList.size,
            contentType = { MovieUiDto::class },
            key = { movies.peek(it)!!.id },

            ) {

            MovieCard(
                movie = movies[it]!!,
                onCardClicked = navController::navigateToMovieDetails,
            )
        }

        when (val state = movies.loadState.append) {
            is LoadState.Error -> {

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
                        LoadingScreen()
                    }
                }
            }

            else -> {}
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    onCardClicked: (Int) -> Unit,
) {

    Surface(
        shape = moviesCardShape, color = BlackTransparent28,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(vertical = 8.dp)
            .clickable { onCardClicked(movie.id) }

    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {
            val coroutineScope = rememberCoroutineScope()
            AsyncImage(
                model = getTmdbImageLink(movie.backdropPath ?: movie.posterPath),
                contentDescription = movie.title + "image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(144.dp)
                    .clip(moviesCardShape)
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
                    text = movie.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = movie.releaseDate.take(4) + ". genre/genre ." + movie.originalLanguage,
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
                            text = movie.voteAverage.toString(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                    }
                    Text(
                        text = movie.popularity.toString(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )

                }
            }
        }


    }
}


val moviesCardShape = mainShape(cornerRadiusDegree = 100f, slopeLength = 30f)