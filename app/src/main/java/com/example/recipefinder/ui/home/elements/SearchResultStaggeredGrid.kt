package com.example.recipefinder.ui.home.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipefinder.data.model.Recipe


@Composable
fun SearchResultStaggeredGrid(
    modifier: Modifier,
    searchRecipeByIngredients: List<Recipe>,
    onRecipeClick: (Int) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(searchRecipeByIngredients.size) { photo ->
                RecipeHorizontalListItem(searchRecipeByIngredients[photo]) {
                    onRecipeClick(it)
                }
            }
        },
        modifier = modifier.then(Modifier.fillMaxSize())
    )
}
