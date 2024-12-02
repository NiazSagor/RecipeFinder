package com.example.recipefinder

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.recipefinder.data.worker.GetRandomRecipesWorker
import com.example.recipefinder.ui.navigation.RecipeFinderNavGraph
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
                val navController = rememberNavController()
                RecipeFinderNavGraph(navController)
            }
        }
        // TODO: commented out for now, there are random recipes in the datastore
        //GetRandomRecipesWorker.enqueueOneTimeWork(this)
    }
}
