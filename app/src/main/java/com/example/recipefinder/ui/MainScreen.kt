package com.example.recipefinder.ui

import android.annotation.SuppressLint
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.recipefinder.ui.home.components.BottomNavigationBar
import com.example.recipefinder.ui.navigation.RecipeFinderDestinations.COMMUNITY_ROUTE
import com.example.recipefinder.ui.navigation.RecipeFinderDestinations.HOME_ROUTE
import com.example.recipefinder.ui.navigation.RecipeFinderDestinations.PROFILE_ROUTE
import com.example.recipefinder.ui.navigation.RecipeFinderNavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {
            BottomAppBar {
                BottomNavigationBar {
                    if (it == "Profile") {
                        navController.navigate(PROFILE_ROUTE) {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    } else if (it == "Home") {
                        navController.navigate(HOME_ROUTE) {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    } else if (it == "Community") {
                        navController.navigate(COMMUNITY_ROUTE) {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                }
            }
        }
    ) { padding ->
        RecipeFinderNavGraph(
            paddingValues = padding,
            navController = navController
        )
    }
}
