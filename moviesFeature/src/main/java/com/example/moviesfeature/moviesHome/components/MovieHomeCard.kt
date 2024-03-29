package com.example.moviesfeature.moviesHome.components

import androidx.compose.runtime.Composable
import com.example.moviesfeature.uiModels.MovieUiDto
import com.example.sharedui.base.BaseImageCard
import com.example.sharedui.base.home.BaseHomeCard
import com.example.sharedui.x.imageCardModifier

@Composable
fun MovieHomeCard(movie: MovieUiDto, onClick: (Int) -> Unit) {
    BaseHomeCard(
        id = movie.id,
        title =movie.title ,
        date = movie.releaseDate,
        genres = movie.genres,
        voteAverage = movie.voteAverage,
        posterPath = movie.posterPath,
        backdropPath =movie.backdropPath ,
        onClick = onClick,
    ){
        HomeCardImage(title = movie.title, posterPath =movie.posterPath , backdropPath =movie.backdropPath )
    }


}

@Composable
fun HomeCardImage(title: String, posterPath: String?, backdropPath: String?) {
    BaseImageCard(
        imagePath = posterPath ?: backdropPath,
        title = title,
        modifier = imageCardModifier
    )
}

