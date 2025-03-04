package com.example.recipefinder.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.ui.home.components.LottieLoadingIndicator
import com.example.recipefinder.ui.home.elements.RecipeHorizontalListItem
import com.example.recipefinder.ui.home.elements.SearchSection
import com.example.recipefinder.ui.home.elements.TopRecipeCard

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onRecipeClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) { paddingValues ->
        val homeState by viewModel.homeState.collectAsStateWithLifecycle()
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // home
            // search,
            SearchSection(
                bottomPadding = paddingValues.calculateBottomPadding(),
                viewModel = viewModel,
                modifier = Modifier.fillMaxWidth(),
                onRecipeClick = onRecipeClick
            )
            when (homeState) {
                is HomeState.Error -> {}
                HomeState.Idle, HomeState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { LottieLoadingIndicator() }
                }

                is HomeState.Success -> {
                    //
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                        ),
                    ) {
                        val chunkedList = (homeState as HomeState.Success).randomRecipes.chunked(10)
                        item {
                            if (chunkedList.size > 2 && chunkedList[2].isNotEmpty()) {
                                // top image
                                TopRecipeCard(
                                    onRecipeClick = onRecipeClick,
                                    recipe = chunkedList[2].last { it.readyInMinutes > 0 }
                                )
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.size(16.dp))
                        }

                        itemsIndexed(chunkedList) { index, recipes: List<Recipe> ->
                            when (index) {
                                0 -> HorizontalList(
                                    getLikesForRecipe = { viewModel.getRecipeLikeCount(it) },
                                    onRecipeClick = onRecipeClick,
                                    onSave = { viewModel.save(it) },
                                    title = "Try something new",
                                    recipes = recipes
                                )

                                1 -> HorizontalList(
                                    getLikesForRecipe = { viewModel.getRecipeLikeCount(it) },
                                    onRecipeClick = onRecipeClick,
                                    onSave = { viewModel.save(it) },
                                    title = "Fancy snacks!",
                                    recipes = recipes
                                )

                                2 -> HorizontalList(
                                    getLikesForRecipe = { viewModel.getRecipeLikeCount(it) },
                                    onRecipeClick = onRecipeClick,
                                    onSave = { viewModel.save(it) },
                                    title = "Holiday!",
                                    recipes = recipes
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HorizontalList(
    getLikesForRecipe: suspend (Int) -> Int,
    onRecipeClick: (Int) -> Unit,
    onSave: (Recipe) -> Unit,
    title: String,
    recipes: List<Recipe>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp)
    ) {
        // list item heading
        Text(
            text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // list of recipes row
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(recipes) { index, recipe: Recipe ->
                RecipeHorizontalListItem(
                    getLikesForRecipe = getLikesForRecipe,
                    recipe = recipe,
                    searchItem = false,
                    onRecipeClick = onRecipeClick,
                    onSave = onSave
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeContent() {
}

@Preview(showBackground = true)
@Composable
fun PreviewHorizontalTrendingList() {

}