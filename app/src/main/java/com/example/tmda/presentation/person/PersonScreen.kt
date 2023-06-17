package com.example.tmda.presentation.person

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.tmda.presentation.series.seriesHome.ItemsLazyRowComponent
import com.example.tmda.presentation.shared.AppToolBar
import com.example.tmda.presentation.shared.UiStates.UiState

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


