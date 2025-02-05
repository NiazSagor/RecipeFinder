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
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.example.recipefinder.ui.home.elements.TopContainer
import com.example.recipefinder.ui.home.elements.TopRecipeCard

@Composable
fun Home(

) {

}

@Composable
fun HomeContent(
    onRecipeClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    Scaffold { paddingValues ->
        val homeState by viewModel.homeState.collectAsStateWithLifecycle()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            TopContainer(
                bottomPadding = paddingValues.calculateBottomPadding(),
                modifier = Modifier.fillMaxWidth()
            ) {
                onRecipeClick(it)
            }
            when (homeState) {
                is HomeState.Error -> {}
                HomeState.Idle, HomeState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { LottieLoadingIndicator() }
                }

                is HomeState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                        ),
                    ) {
                        val chunkedList = (homeState as HomeState.Success).randomRecipes.chunked(10)
                        val list1 = chunkedList.getOrNull(0) ?: emptyList()
                        val list2 = chunkedList.getOrNull(1) ?: emptyList()
                        val list3 = chunkedList.getOrNull(2) ?: emptyList()
                        item {
                            if (list3.isNotEmpty()) {
                                TopRecipeCard(
                                    onRecipeClick,
                                    list3.last { it.readyInMinutes > 0 })
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.size(16.dp))
                        }

                        item {
                            if (list1.isNotEmpty()) {
                                HorizontalList(
                                    getLikesForRecipe = {
                                        viewModel.getRecipeLike(it)
                                    },
                                    onRecipeClick = onRecipeClick,
                                    onSave = { viewModel.save(it) },
                                    title = "Try something new",
                                    recipes = list1,
                                )
                            }
                        }
                        item {
                            if (list2.isNotEmpty()) {
                                HorizontalList(
                                    getLikesForRecipe = {
                                        viewModel.getRecipeLike(it)
                                    },
                                    onRecipeClick = onRecipeClick,
                                    onSave = { viewModel.save(it) },
                                    "Fancy snacks!",
                                    list2
                                )
                            }
                        }
                        item {
                            if (list3.isNotEmpty()) {
                                HorizontalList(
                                    getLikesForRecipe = {
                                        viewModel.getRecipeLike(it)
                                    }, onRecipeClick,
                                    onSave = { viewModel.save(it) }, "Holiday!", list3
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
        Text(
            text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        val listState = rememberLazyListState()
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(recipes) { index, recipe ->
                RecipeHorizontalListItem(getLikesForRecipe, recipe, false, onRecipeClick, onSave)
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