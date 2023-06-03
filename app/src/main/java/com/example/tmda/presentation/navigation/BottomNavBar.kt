package com.example.tmda.presentation.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tmda.R
import com.example.tmda.ui.theme.GreyMetallic
import com.example.tmda.ui.theme.PineGreen
import com.example.tmda.ui.theme.WhiteTransparent60

@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar(
        Modifier.height(70.dp),
        contentColor = GreyMetallic,
        containerColor = GreyMetallic


    ) {
        val backStackEntry = navController.currentBackStackEntryAsState()
        bottomNavItems.forEach { item ->

            val selected = item.route == backStackEntry.value?.destination?.parent?.route
            NavigationBarItem(
                selected = selected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PineGreen,
                    unselectedIconColor = WhiteTransparent60,
                    indicatorColor = GreyMetallic
                ),
                onClick = { navController.navigate(item.route) },
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
        route = moviesRoute,
        iconId = R.drawable.ic_movie,
    ),
    BottomNavItem(
        name = "Series",
        route = seriesRoute,
        iconId = R.drawable.ic_tv,
    ),
    BottomNavItem(
        name = "Search",
        route = searchRoute,
        iconId = R.drawable.ic_search,
    ),
    BottomNavItem(
        name = "Account",
        route = accountRoute,
        iconId = R.drawable.ic_user,
    ),
)

data class BottomNavItem(
    val name: String,
    val route: String,
    val iconId: Int,
)