package com.example.tmda.presentation.shared

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.tmda.R
import com.example.tmda.presentation.shared.uiStates.LoadingScreen
import com.example.tmda.presentation.shared.uiStates.UiState
import com.example.tmda.ui.theme.PineGreenDark

@Composable
fun SavedItemIcon(
    modifier: Modifier,
    isSavedState: UiState<Boolean>
) {
    when(isSavedState){
        is UiState.Failure -> TODO()
        is UiState.Loading -> {
            LoadingScreen()
        }
        is UiState.Success -> {
            val isSaved=isSavedState.data
            if (isSaved)
                Icon(
                    painterResource(id = R.drawable.ic_bookmark_filled),
                    contentDescription = null,
                    tint = PineGreenDark,
                    modifier = modifier
                )
            else
                Icon(
                    painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = null,
                    tint = PineGreenDark,
                    modifier = modifier
                )
        }
    }

}
@Composable
fun SavedItemIcon(
    modifier: Modifier,
    isSavedState: MutableState<Boolean>
) {


    if (isSavedState.value)
        Icon(
            painterResource(id = R.drawable.ic_bookmark_filled),
            contentDescription = null,
            tint = PineGreenDark,
            modifier = modifier
        )
    else
        Icon(
            painterResource(id = R.drawable.ic_bookmark),
            contentDescription = null,
            tint = PineGreenDark,
            modifier = modifier
        )


}