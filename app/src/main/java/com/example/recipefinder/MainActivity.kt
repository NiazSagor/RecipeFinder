package com.example.recipefinder

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.recipefinder.ui.home.HomeContent
import com.example.recipefinder.ui.theme.RecipeFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.GREEN, Color.GREEN)
        )
        super.onCreate(savedInstanceState)
        setContent {
            RecipeFinderTheme {
                HomeContent()
            }
        }
    }
}
