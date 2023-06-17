package com.example.tmda.presentation.shared.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tmda.presentation.movies.moviesList.ErrorComponent
import com.example.tmda.presentation.shared.UiStates.LoadingScreen
import com.example.tmda.presentation.shared.UiStates.UiState
import com.example.tmda.ui.theme.PineGreenDark

@Composable
fun <T> BaseLazyRowComponent(
    title: String = "More like this",
    hasBottomDivider: Boolean = true,
    onSeeAllClicked: () -> Unit,
    itemsState: UiState<List<T>>,
    onItemClicked: (Int) -> Unit,
    contentCard: @Composable (T, (Int) -> Unit) -> Unit
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Divider(
                modifier = Modifier
                    .height(20.dp)
                    .width(5.dp), thickness = 1.dp, color = PineGreenDark
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        }
        TextButton(onClick = onSeeAllClicked, contentPadding = PaddingValues(0.dp)) {
            Text(
                text = "See All",
                color = PineGreenDark,
                style = MaterialTheme.typography.titleSmall
            )

        }
    }

    when (val items = itemsState) {
        is UiState.Failure -> ErrorComponent {}
        is UiState.Loading -> LoadingScreen(modifier = Modifier.height(360.dp))
        is UiState.Success -> {
            val data = items.data
            LazyRow {
                item { Spacer(modifier = Modifier.width(16.dp)) }
                items(data.size) { contentCard(data[it], onItemClicked) }
                item { Spacer(modifier = Modifier.width(16.dp)) }
            }

        }

    }
    if (hasBottomDivider) Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 32.dp)
    ) else Spacer(modifier = Modifier.height(16.dp))


}