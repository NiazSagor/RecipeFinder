package com.example.recipefinder.ui.home.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipefinder.data.model.SearchRecipeByIngredients


@Composable
fun SearchResultStaggeredGrid(
    modifier: Modifier,
    searchRecipeByIngredients: List<SearchRecipeByIngredients>,
    onRecipeClick: (Int) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(searchRecipeByIngredients.size) { photo ->
                SearchResultStaggeredListItem(searchRecipeByIngredients[photo]) {
                    onRecipeClick(it)
                }
            }
        },
        modifier = modifier.then(Modifier.fillMaxSize())
    )
}
