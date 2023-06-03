package com.bitIO.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.bitIO.ui.START_DESTINATION
import com.bitIO.ui.TvShowNavGraph
import com.bitIO.ui.screens.tvshow.TvShowScreen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

//@AndroidEntryPoint
//class TvShowActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MaterialTheme {
//                val navController = rememberNavController()
//                TvShowNavGraph(navController = navController, START_DESTINATION)
//            }
//        }
//    }
//
//}