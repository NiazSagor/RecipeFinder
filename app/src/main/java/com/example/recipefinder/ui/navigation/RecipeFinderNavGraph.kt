package com.example.recipefinder.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.recipefinder.ui.community.postcomments.PostCommentsScreen
import com.example.recipefinder.ui.community.posts.CommunityScreen
import com.example.recipefinder.ui.home.HomeScreen
import com.example.recipefinder.ui.post.PostRecipeScreen
import com.example.recipefinder.ui.profile.components.ProfileScreen
import com.example.recipefinder.ui.recipedetails.RecipeDetailsScreen
import com.example.recipefinder.ui.recipetipdetails.RecipeTipDetailsScreen
import com.example.recipefinder.ui.signin.SignInScreen

// TODO: fix bottom nav
@Composable
fun RecipeFinderNavGraph(
    paddingValues: PaddingValues,
    navController: NavHostController,
    startDestination: String = RecipeFinderDestinations.HOME_ROUTE,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        val navigationActions = RecipeFinderNavigation(navController)

        composable(
            route = RecipeFinderDestinations.HOME_ROUTE
        ) {
            HomeScreen(
                paddingValues = paddingValues,
                onRecipeClick = { navigationActions.navigateToRecipeDetailsScreen(it) },
            )
        }

        composable(
            route = RecipeFinderDestinations.COMMUNITY_ROUTE
        ) {
            CommunityScreen(
                paddingValues = paddingValues,
                onPostClick = { navigationActions.navigateToPostRecipeScreen() },
                onComment = { navigationActions.navigateToPostCommentScreen(it) },
            )
        }

        composable(
            route = RecipeFinderDestinations.POST_RECIPE_ROUTE
        ) {
            PostRecipeScreen(
                paddingValues
            ) {
                navigationActions.popCurrentDestination()
            }
        }

        composable(
            route = RecipeFinderDestinations.PROFILE_ROUTE
        ) {
            ProfileScreen(
                parentPaddingValues = paddingValues,
                onRecipeClick = { navigationActions.navigateToRecipeDetailsScreen(it) },
            )
        }

        composable(
            route = "${RecipeFinderDestinations.RECIPE_DETAILS_ROUTE}/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId")
            if (recipeId != null) {
                RecipeDetailsScreen(
                    paddingValues = paddingValues,
                    recipeId = recipeId,
                    onPopCurrent = { navigationActions.popCurrentDestination() },
                    onTipClick = { navigationActions.navigateToMakeTipScreen(recipeId) },
                    onTipDetailsClick = {
                        navigationActions.navigateToRecipeTipDetailsScreen(
                            recipeId
                        )
                    }
                )
            }
        }

        composable(
            route = "${RecipeFinderDestinations.RECIPE_TIP_DETAILS_ROUTE}/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId")
            if (recipeId != null) {
                RecipeTipDetailsScreen(
                    recipeId = recipeId,
                    onPopCurrent = {
                        navigationActions.popCurrentDestination()
                    }
                )
            }
        }

        composable(
            route = "${RecipeFinderDestinations.COMMUNITY_POST_COMMENT_ROUTE}/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) {
            val postId = it.arguments?.getString("postId")
            if (postId != null) {
                PostCommentsScreen(
                    paddingValues = paddingValues,
                    postId = postId
                )
            }
        }

        composable(
            route = RecipeFinderDestinations.SIGN_IN_ROUTE
        ) {
            SignInScreen(
                paddingValues = paddingValues
            ) {
                navigationActions.popCurrentDestination()
                navigationActions.navigateToProfileScreen()
            }
        }
    }
}
