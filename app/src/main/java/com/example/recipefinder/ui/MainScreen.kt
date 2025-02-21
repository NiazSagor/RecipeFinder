package com.example.recipefinder.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.recipefinder.ui.home.components.BottomNavigationBar
import com.example.recipefinder.ui.navigation.RecipeFinderDestinations.COMMUNITY_ROUTE
import com.example.recipefinder.ui.navigation.RecipeFinderDestinations.HOME_ROUTE
import com.example.recipefinder.ui.navigation.RecipeFinderDestinations.PROFILE_ROUTE
import com.example.recipefinder.ui.navigation.RecipeFinderDestinations.SIGN_IN_ROUTE
import com.example.recipefinder.ui.navigation.RecipeFinderNavGraph
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController
) {
    var user = Firebase.auth.currentUser
    Scaffold(
        bottomBar = {
            BottomAppBar {
                BottomNavigationBar {
                    user = Firebase.auth.currentUser
                    // TODO: fix the scenario when the sign in is successful
                    if (it == "Profile") {
                        if (user == null) {
                            navController.navigate(SIGN_IN_ROUTE) {
                                navController.graph.startDestinationRoute?.let { screen_route ->
                                    popUpTo(screen_route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        } else {
                            navController.navigate(PROFILE_ROUTE) {
                                navController.graph.startDestinationRoute?.let { screen_route ->
                                    popUpTo(screen_route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
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
                        if (user == null) {
                            navController.navigate(SIGN_IN_ROUTE) {
                                navController.graph.startDestinationRoute?.let { screen_route ->
                                    popUpTo(screen_route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        } else {
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
        }
    ) { padding ->
        RecipeFinderNavGraph(
            paddingValues = padding,
            navController = navController
        )
    }
}
