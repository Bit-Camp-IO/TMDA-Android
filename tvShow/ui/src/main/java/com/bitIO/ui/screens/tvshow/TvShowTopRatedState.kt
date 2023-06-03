//package com.bitIO.ui.screens.tvshow
//
//import com.bitIO.tvshowcomponent.domain.entity.TvShow
//
//data class TvShowTopRatedState(
//    val tvShowUiState: List<TvShowUiState> = emptyList(),
//    val isLoading: Boolean = true,
//    val error: String = ""
//)
//
//data class TvShowUiState(
//    val name: String = "",
//    val id: Int = 0,
//    val imageUrl: String = ""
//)
//
//internal fun TvShow.toUiState(): TvShowUiState {
//    return TvShowUiState(
//        id = id,
//        name = title,
//        imageUrl = imageURL
//    )
//}
//
//internal fun List<TvShow>.toUiState(): List<TvShowUiState> = map { it.toUiState() }