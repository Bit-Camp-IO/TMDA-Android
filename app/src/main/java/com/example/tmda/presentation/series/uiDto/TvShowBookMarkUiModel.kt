package com.example.tmda.presentation.series.uiDto

import androidx.compose.runtime.MutableState

data class TvShowBookMarkUiModel(
    val tvShowUiModel: TvShowUiModel,
    val bookMarkState:MutableState<Boolean>
)
