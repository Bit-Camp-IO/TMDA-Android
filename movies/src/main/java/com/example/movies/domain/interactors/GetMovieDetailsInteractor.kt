package com.example.movies.domain.interactors

import com.example.movies.data.mappers.toMovieDetails
import com.example.movies.domain.enities.MovieDetails
import com.example.movies.domain.repositories.MoviesRepository
import com.example.shared.states.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetMovieDetailsInteractor(private val repo: MoviesRepository) {
    operator fun invoke(movieId: Int): Flow<State<MovieDetails>> = flow {
        try {
            emit(State.Loading())
            val movieDetails = repo.getMovieDetails(movieId).toMovieDetails()
            emit(State.Success(movieDetails))
        } catch (e: HttpException) {
            emit(State.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(State.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}