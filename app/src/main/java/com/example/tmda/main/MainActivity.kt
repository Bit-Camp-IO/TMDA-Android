@file:OptIn(FlowPreview::class)

package com.example.tmda.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tmda.presentation.login.LoginScreen
import com.example.tmda.presentation.navigation.BottomNavBar
import com.example.tmda.presentation.navigation.NavigationHost
import com.example.tmda.presentation.shared.BackGround
import com.example.tmda.presentation.shared.uiStates.LoadingScreen
import com.example.tmda.ui.theme.TMDATheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @OptIn(FlowPreview::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            TMDATheme {
                Box {
                    BackGround()
                    LoginStateHandler(
                        currentState = viewModel.userState.value,
                        navController = navController
                    )

                }

            }
        }

    }


}

@Composable
fun LoginStateHandler(currentState: MainViewModel.LoginState, navController: NavHostController) {
    when (currentState) {
        MainViewModel.LoginState.LoggedIn -> MainScreen(navController = navController)
        MainViewModel.LoginState.LoggedOut -> LoginScreen()
        MainViewModel.LoginState.Loading -> LoadingScreen()
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
