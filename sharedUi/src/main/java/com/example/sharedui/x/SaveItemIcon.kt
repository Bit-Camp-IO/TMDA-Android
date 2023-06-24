package com.example.sharedui.x

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sharedui.R
import com.example.sharedui.colors.PineGreenDark
import com.example.sharedui.uiStates.UiState


@Composable
fun BookMarkComponent(modifier: Modifier, isSavedState: UiState<Boolean>, onClick: () -> Unit) {
    Box(modifier = Modifier.clickable { onClick() }) {
        when (isSavedState) {
            is UiState.Failure -> {
                BookmarkIcon(
                    iconRes = R.drawable.ic_bookmark_filled,
                    text = "reload",
                    color = Color.Red
                )
            }

            is UiState.Loading -> {

                BookmarkIcon(
                    iconRes = R.drawable.ic_bookmark_filled,
                    text = "loading",
                    color = Color.Magenta
                )
            }

            is UiState.Success -> {
                val isSaved = isSavedState.data
                if (isSaved)
                    BookmarkIcon(iconRes = R.drawable.ic_bookmark_filled, text = "Remove")
                else
                    BookmarkIcon(iconRes = R.drawable.ic_bookmark, text = "Add")

            }
        }
    }

}

@Composable
fun BookmarkIcon(
    iconRes: Int,
    text: String,
    color: Color = PineGreenDark, ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painterResource(id = iconRes),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        //Text(text = text)
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