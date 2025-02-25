package com.example.recipefinder.ui.profile.savedrecipe;


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.ui.home.elements.RecipeHorizontalListItem
import com.example.recipefinder.ui.profile.ProfileScreenViewModel


@Composable
fun SavedRecipeScreen(
    paddingValues: PaddingValues,
    recipes: List<Recipe>,
    viewmodel: ProfileScreenViewModel,
    onRecipeClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            content = {
                itemsIndexed(recipes) { index, recipe ->
                    RecipeHorizontalListItem(
                        getLikesForRecipe = { viewmodel.getRecipeLike(it) },
                        recipe = recipe,
                        searchItem = true,
                        onRecipeClick = { onRecipeClick(it) },
                        onSave = { viewmodel.saveRecipe(it) }
                    )
                }
            },
        )
    }
}
