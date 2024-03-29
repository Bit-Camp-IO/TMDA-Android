@file:OptIn(FlowPreview::class)

package com.example.tmda.main

import android.os.Bundle
import android.view.View
import android.view.WindowManager
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sharedui.x.BackGround
import com.example.tmda.navigation.BottomNavBar
import com.example.tmda.navigation.NavigationHost
import com.example.tmda.ui.theme.TMDATheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // WindowCompat.setDecorFitsSystemWindows(window,false)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )


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
        MainViewModel.LoginState.LoggedOut -> com.example.profilefeature.login.LoginScreen()
        MainViewModel.LoginState.Loading -> com.example.sharedui.uiStates.LoadingScreen()
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
