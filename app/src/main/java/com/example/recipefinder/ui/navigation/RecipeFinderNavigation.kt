package com.example.recipefinder.ui.navigation

import androidx.navigation.NavController


object RecipeFinderDestinations {
    const val HOME_ROUTE = "home_route"
    const val PROFILE_ROUTE = "profile_route"
    const val COMMUNITY_ROUTE = "community_route"
    const val SIGN_IN_ROUTE = "sign_in_route"
    const val COMMUNITY_POST_COMMENT_ROUTE = "community_post_comment_route"
    const val POST_RECIPE_ROUTE = "post_recipe_route"
    const val RECIPE_DETAILS_ROUTE = "recipe_details_route"
    const val RECIPE_TIP_DETAILS_ROUTE = "recipe_tip_details_route"
    const val MAKE_RECIPE_TIP_ROUTE = "make_recipe_tip_route"
}

class RecipeFinderNavigation(
    navController: NavController
) {

    val navigateToRecipeDetailsScreen: (recipeId: Int) -> Unit = {
        navController.navigate("${RecipeFinderDestinations.RECIPE_DETAILS_ROUTE}/$it") {
            launchSingleTop = false
        }
    }

    val navigateToRecipeTipDetailsScreen: (recipeId: Int) -> Unit = {
        navController.navigate("${RecipeFinderDestinations.RECIPE_TIP_DETAILS_ROUTE}/$it") {
            launchSingleTop = false
        }
    }

    val navigateToMakeTipScreen: (recipeId: Int) -> Unit = {
        navController.navigate("${RecipeFinderDestinations.MAKE_RECIPE_TIP_ROUTE}/$it") {
            launchSingleTop = false
        }
    }

    val popCurrentDestination: () -> Unit = {
        navController.popBackStack()
    }

    val navigateToPostRecipeScreen: () -> Unit = {
        navController.navigate(RecipeFinderDestinations.POST_RECIPE_ROUTE) {
            launchSingleTop = false
        }
    }

    val navigateToPostCommentScreen: (postId: String) -> Unit = {
        navController.navigate("${RecipeFinderDestinations.COMMUNITY_POST_COMMENT_ROUTE}/$it") {
            launchSingleTop = false
        }
    }

    val navigateToProfileScreen: () -> Unit = {
        navController.navigate(RecipeFinderDestinations.PROFILE_ROUTE) {
            launchSingleTop = false
        }
    }

    val navigateToSignInScreen: () -> Unit = {
        navController.navigate(RecipeFinderDestinations.SIGN_IN_ROUTE) {
            launchSingleTop = false
        }
    }
}
