package com.example.recipefinder.ui.home.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipefinder.data.dummy.recipeList
import com.example.recipefinder.data.model.Recipe


@Composable
fun SearchResultStaggeredGrid(
    getLikesForRecipe: suspend (Int) -> Int,
    modifier: Modifier,
    searchResult: List<Recipe>,
    onRecipeClick: (Int) -> Unit,
    onSave: (Recipe) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier.then(Modifier.fillMaxSize()),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp,
        content = {
            itemsIndexed(searchResult) { _, recipe: Recipe ->
                RecipeHorizontalListItem(
                    getLikesForRecipe = getLikesForRecipe,
                    recipe = recipe,
                    searchItem = true,
                    onRecipeClick = { recipeId ->
                        onRecipeClick(recipeId)
                    },
                    onSave = { recipe ->
                        onSave(recipe)
                    }
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewSearchResultStaggeredGrid() {
    SearchResultStaggeredGrid(
        getLikesForRecipe = { 1 },
        modifier = Modifier,
        searchResult = recipeList,
        onRecipeClick = {},
        onSave = {}
    )
}
