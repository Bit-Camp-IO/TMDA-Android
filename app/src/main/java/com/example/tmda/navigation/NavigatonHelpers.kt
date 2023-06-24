package com.example.tmda.navigation

import androidx.navigation.NavController
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit


fun NavController.navigateToMovieListScreen(
    screenTitle: String,
    moviesScreenType: com.example.moviesfeature.uiModels.MoviesScreenType,
    id: Int = -1
) {
    NavigationOrganizer.addToEventsStream {
        val parent = currentBackStackEntry?.destination?.parent?.route
        navigate("${parent + Destinations.MOVIES_LIST_SCREEN}/$screenTitle/$moviesScreenType/${id}")
    }

}

fun NavController.navigateToMovieDetails(movieId: Int) {
    NavigationOrganizer.addToEventsStream {
        val parent = currentBackStackEntry?.destination?.parent?.route
        navigate("${parent + Destinations.MOVIES_DETAILS_SCREEN}/$movieId")
    }

}

fun NavController.navigateToPersonScreen(personId: Int) {
    NavigationOrganizer.addToEventsStream {
        val parent = currentBackStackEntry?.destination?.parent?.route
        navigate("${parent + Destinations.PERSON_SCREEN}/$personId")
    }

}

//
fun NavController.navigateToTvShowsListScreen(
    seriesScreenType: com.example.tvshowfeature.seriesList.SeriesScreenType,
    id: Int = -1
) {
    NavigationOrganizer.addToEventsStream {
        val parent = currentBackStackEntry?.destination?.parent?.route
        navigate("${parent + Destinations.SERIES_LIST_SCREEN}/$seriesScreenType/$id")
    }

}

fun NavController.navigateToTvShowDetailsScreen(seriesId: Int) {
    NavigationOrganizer.addToEventsStream {
        val parent = currentBackStackEntry?.destination?.parent?.route
        navigate("${parent + Destinations.SERIES_DETAILS_SCREEN}/$seriesId")
    }

}

fun NavController.getOrganizer(): NavigationOrganizer {
    return NavigationOrganizer
}

object NavigationOrganizer {
    private val navActionStream = PublishSubject.create<() -> Unit>()
    private val debouncedStream = navActionStream.throttleFirst(500, TimeUnit.MILLISECONDS)


    fun addToEventsStream(event: () -> Unit) {
        navActionStream.onNext(event)
    }

    init {
        observeDebouncedEvents()
    }

    private fun observeDebouncedEvents() = debouncedStream.subscribe { it.invoke() }

}


