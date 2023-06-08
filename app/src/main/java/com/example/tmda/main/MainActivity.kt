package com.example.tmda.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tmda.presentation.login.LoginScreen
import com.example.tmda.presentation.navigation.BottomNavBar
import com.example.tmda.presentation.navigation.NavigationHost
import com.example.tmda.presentation.shared.BackGround
import com.example.tmda.ui.theme.TMDATheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            TMDATheme {
                Box {
                    BackGround()
                    AppLoginStateHandler(
                        loginState = viewModel.userState.value,
                        navController = navController
                    )

                }

            }
        }
    }
}

@Composable
fun AppLoginStateHandler(loginState: MainViewModel.LoginState, navController: NavHostController) {
    when (loginState) {
        MainViewModel.LoginState.Loading -> LoadingScreen()
        MainViewModel.LoginState.LoggedOut -> LoginScreen()
        MainViewModel.LoginState.LoggedIn -> MainScreen(navController)
    }


}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator()
    }
}

@Composable
fun ErrorScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }

}

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        contentWindowInsets = WindowInsets(top = 0.dp),
        bottomBar = { BottomNavBar(navController = navController) },
    ) {
        Box(Modifier.padding(bottom = it.calculateBottomPadding())) {
            NavigationHost(navController = navController)
        }

    }

}
