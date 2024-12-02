package com.example.recipefinder.ui.navigation

import androidx.navigation.NavController


object RecipeFinderDestinations {
    const val HOME_ROUTE = "home_route"
    const val RECIPE_DETAILS_ROUTE = "recipe_details_route"
}

class RecipeFinderNavigation(
    navController: NavController
) {

    val navigateToRecipeDetailsScreen: (recipeId: Int) -> Unit = {
        navController.navigate("${RecipeFinderDestinations.RECIPE_DETAILS_ROUTE}/$it") {
            launchSingleTop = true
        }
    }

    val popCurrentDestination: () -> Unit = {
        navController.popBackStack()
    }

}
