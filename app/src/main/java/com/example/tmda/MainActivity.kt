package com.example.tmda

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.bitIO.tvshowcomponent.data.remote.TvShowApiService
import com.bitIO.tvshowcomponent.data.repository.TvShowRepositoryImp
import com.example.tmda.infrastructure.remote.TmdaApiServices
import com.example.tmda.infrastructure.remote.TmdaRemoteDataSource
import com.example.tmda.presentation.navigation.BottomNavBar
import com.example.tmda.presentation.navigation.NavigationHost
import com.example.tmda.presentation.shared.BackGround
import com.example.tmda.ui.theme.TMDATheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
            TMDATheme {
                Box {

                    BackGround()
                    Scaffold(
                        contentWindowInsets = WindowInsets(top = 0.dp),
                        bottomBar = { BottomNavBar(navController = navController) }
                    ) {
                        Box(Modifier.padding(it)) {
                            NavigationHost(navController = navController)
                        }
                    }
                }

            }
        }
    }
}


