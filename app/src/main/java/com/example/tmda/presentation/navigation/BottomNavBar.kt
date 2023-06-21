package com.example.tmda.presentation.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tmda.R
import com.example.tmda.ui.theme.GreyMetallic
import com.example.tmda.ui.theme.PineGreenDark
import com.example.tmda.ui.theme.WhiteTransparent60


@Composable
fun BottomNavBar(navController: NavController) {

    NavigationBar(
        Modifier
            .height(60.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        contentColor = GreyMetallic,
        containerColor = GreyMetallic
    ) {

        val backStackEntry = navController.currentBackStackEntryAsState()
        bottomNavItems.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.parent?.route
            NavigationBarItem(
                modifier = Modifier.padding(top = 8.dp),
                selected = selected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PineGreenDark,
                    unselectedIconColor = WhiteTransparent60,
                    indicatorColor = GreyMetallic
                ),
                onClick = {
                    navController.getOrganizer().addToEventsStream {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }

                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = "${item.name} Icon",
                    )
                }
            )
        }
    }
}


val bottomNavItems = listOf(
    BottomNavItem(
        name = "Movies",
        route = Destinations.MOVIES_ROUTE,
        iconId = R.drawable.ic_movie,
    ),
    BottomNavItem(
        name = "Series",
        route = Destinations.SERIES_ROUTE,
        iconId = R.drawable.ic_tv,
    ),
    BottomNavItem(
        name = "Search",
        route = Destinations.SEARCH_ROUTE,
        iconId = R.drawable.ic_search,
    ),
    BottomNavItem(
        name = "Account",
        route = Destinations.ACCOUNT_ROUTE,
        iconId = R.drawable.ic_user,
    ),
)

data class BottomNavItem(
    val name: String,
    val route: String,
    val iconId: Int,
)
