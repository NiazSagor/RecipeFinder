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
    getLikesForRecipe: suspend (Int) -> Int,
    modifier: Modifier,
    searchRecipeByIngredients: List<Recipe>,
    onRecipeClick: (Int) -> Unit,
    onSave: (Recipe) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(searchRecipeByIngredients.size) { photo ->
                RecipeHorizontalListItem(
                    getLikesForRecipe = getLikesForRecipe,
                    recipe = searchRecipeByIngredients[photo],
                    searchItem = true,
                    onRecipeClick = { onRecipeClick(it) },
                    onSave = { onSave(it) }
                )
            }
        },
        modifier = modifier.then(Modifier.fillMaxSize())
    )
}
