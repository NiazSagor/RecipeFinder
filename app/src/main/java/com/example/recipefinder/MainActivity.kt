package com.example.recipefinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.recipefinder.data.worker.GetRandomRecipesWorker
import com.example.recipefinder.ui.MainScreen
import com.example.recipefinder.ui.theme.RecipeFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.light(Color.WHITE, Color.WHITE)
//        )
        super.onCreate(savedInstanceState)
        setContent {
            RecipeFinderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    MainScreen(navController = navController)
                }
            }
        }
        // TODO: commented out for now, there are random recipes in the datastore
        GetRandomRecipesWorker.enqueuePeriodicWork(this)
    }
}
