package com.example.recipefinder.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.ui.home.components.BottomNavigationBar
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
    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = paddingValues.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding()
                ),
            ) {

                when (homeState) {
                    is HomeState.Error -> {}
                    HomeState.Idle -> {}
                    HomeState.Loading -> {}
                    is HomeState.Success -> {
                        val chunkedList = (homeState as HomeState.Success).randomRecipes.chunked(10)
                        val list1 = chunkedList.getOrNull(0) ?: emptyList()
                        val list2 = chunkedList.getOrNull(1) ?: emptyList()
                        val list3 = chunkedList.getOrNull(2) ?: emptyList()
                        item {
                            Column {
                                if (list3.isNotEmpty()) {
                                    TopRecipeCard(
                                        onRecipeClick,
                                        list3.first { it.readyInMinutes > 0 })
                                }
                                Spacer(modifier = Modifier.size(16.dp))
                                if (list1.isNotEmpty()) {
                                    HorizontalList(onRecipeClick, "Trending", list1)
                                }
                                if (list2.isNotEmpty()) {
                                    HorizontalList(
                                        onRecipeClick,
                                        "Popular Recipes This Week",
                                        list2
                                    )
                                }
                                if (list3.isNotEmpty()) {
                                    HorizontalList(onRecipeClick, "Desserts", list3)
                                }
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
    onRecipeClick: (Int) -> Unit,
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
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recipes.size) {
                var isVisible by remember { mutableStateOf(false) }
                LaunchedEffect(recipes[it]) {
                    isVisible = true
                }
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(
                        animationSpec = tween(durationMillis = 500)
                    ),
                ) {
                    RecipeHorizontalListItem(recipes[it], onRecipeClick)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeContent() {
    HomeContent({})
}

@Preview(showBackground = true)
@Composable
fun PreviewHorizontalTrendingList() {

}