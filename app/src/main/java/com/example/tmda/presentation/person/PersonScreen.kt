package com.example.tmda.presentation.person

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shared.entities.Person
import com.example.tmda.R
import com.example.tmda.presentation.movies.getTmdbImageLink
import com.example.tmda.presentation.shared.AppToolBar
import com.example.tmda.presentation.shared.uiStates.ErrorComponent
import com.example.tmda.presentation.shared.uiStates.LoadingScreen
import com.example.tmda.presentation.shared.uiStates.UiState
import com.example.tmda.ui.theme.PineGreenDark

@Composable
fun <T : Any> PersonScreen(
    navController: NavController,
    person: Person,
    itemsState: State<UiState<List<T>>>
) {
    Box {
        AppToolBar(navController = navController) {}
        Column {
            PersonImage(image = person.profilePath, name = person.name)
            PersonDetails(
                birthdate = person.birthday,
                moviesCount = 10,
                overView = person.biography
            )
            ItemsLazyRowComponent(onSeeAllClicked = { /*TODO*/ }, itemsState =itemsState) {
            }
            ItemsLazyRowComponent(onSeeAllClicked = { /*TODO*/ }, itemsState =itemsState) {
            }

        }
    }

}

@Composable
fun PersonImage(image: String?, name: String) {
    Box(
        modifier = Modifier
            .size(360.dp, 450.dp), contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(bottomEnd = 16.dp)),
            model = getTmdbImageLink(image), contentDescription = null
        )
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }

}

@Composable
fun PersonDetails(birthdate: String, moviesCount: Int, overView: String) {
    Column {
        Row {
            Icon(painter = painterResource(id = R.drawable.ic_movie), contentDescription = null)
            Text(text = birthdate, style = MaterialTheme.typography.titleSmall)

        }
        Row {
            Icon(painter = painterResource(id = R.drawable.ic_movie), contentDescription = null)
            Text(text = birthdate, style = MaterialTheme.typography.titleSmall)

        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Divider(Modifier.size(5.dp, 20.dp))
            Text(text = "Overview")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = birthdate, style = MaterialTheme.typography.titleSmall)

    }

}


@Composable
fun <T> ItemsLazyRowComponent(
    title: String = "More like this",
    hasBottomDivider: Boolean = true,
    onSeeAllClicked: () -> Unit,
    itemsState: State<UiState<List<T>>>,
    contentCard: @Composable (T) -> Unit
) {
    when (val items = itemsState.value) {
        is UiState.Failure -> ErrorComponent {}
        is UiState.Loading -> LoadingScreen(modifier = Modifier.height(360.dp))
        is UiState.Success -> {
            val data = items.data

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

            LazyRow {
                item { Spacer(modifier = Modifier.width(16.dp)) }
                items(data.size) { contentCard(data[it]) }
                item { Spacer(modifier = Modifier.width(16.dp)) }
            }
            if (hasBottomDivider) Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 32.dp)
            ) else Spacer(modifier = Modifier.height(16.dp))
        }


        else -> {}
    }
}