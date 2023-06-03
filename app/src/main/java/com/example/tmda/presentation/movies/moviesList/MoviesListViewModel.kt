package com.example.tmda.presentation.movies.moviesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.domain.enities.Movie
import com.example.movies.domain.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(private val repo: MoviesRepository) : ViewModel() {


    fun getMovieList(): Flow<PagingData<Movie>> {
        return repo.getMoviesStream(11).cachedIn(viewModelScope)
    }


}