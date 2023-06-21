package com.example.tmda.presentation.navigation

import androidx.navigation.NavController
import com.example.tmda.presentation.movies.uiModels.MoviesScreenType
import com.example.tmda.presentation.series.seriesList.SeriesScreenType
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit


fun NavController.navigateToMovieListScreen(

    screenTitle: String,
    moviesScreenType: MoviesScreenType,
    id: Int = -1
) {
    getOrganizer().addToEventsStream {
        val parent = currentBackStackEntry?.destination?.parent?.route
        navigate("${parent + Destinations.MOVIES_LIST_SCREEN}/$screenTitle/$moviesScreenType/${id}")
    }

}

fun NavController.navigateToMovieDetails(movieId: Int) {
    getOrganizer().addToEventsStream {
        val parent = currentBackStackEntry?.destination?.parent?.route
        navigate("${parent + Destinations.MOVIES_DETAILS_SCREEN}/$movieId")
    }

}

fun NavController.navigateToPersonScreen(personId: Int) {
    getOrganizer().addToEventsStream {
        val parent = currentBackStackEntry?.destination?.parent?.route
        navigate("${parent + Destinations.PERSON_SCREEN}/$personId")
    }

}

//
fun NavController.navigateToShowsListScreen(seriesScreenType: SeriesScreenType, id: Int = -1) {
    getOrganizer().addToEventsStream {
        val parent = currentBackStackEntry?.destination?.parent?.route
        navigate("${parent + Destinations.SERIES_LIST_SCREEN}/$seriesScreenType/$id")
    }

}

fun NavController.navigateToTvShowDetailsScreen(seriesId: Int) {
    getOrganizer().addToEventsStream {
        val parent = currentBackStackEntry?.destination?.parent?.route
        navigate("${parent + Destinations.SERIES_DETAILS_SCREEN}/$seriesId")
    }

}

fun NavController.getOrganizer(): NavigationOrganizer {
    return NavigationOrganizer
}

object NavigationOrganizer {
    private val navActionStream = PublishSubject.create<() -> Unit>()
    private val debouncedStream = navActionStream.throttleFirst(1000, TimeUnit.MILLISECONDS)


    fun addToEventsStream(event: () -> Unit) {
        navActionStream.onNext(event)
    }

    init {
        observeDebouncedEvents()
    }

    private fun observeDebouncedEvents() = debouncedStream.subscribe { it.invoke() }

}