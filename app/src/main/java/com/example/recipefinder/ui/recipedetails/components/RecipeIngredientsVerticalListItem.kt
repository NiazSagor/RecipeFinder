package com.example.recipefinder.ui.recipedetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun RecipeIngredientsVerticalListItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "apple cider",
            )
            Text(
                text = "6 cups",
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeIngredientsVerticalListItem() {
    RecipeIngredientsVerticalListItem()
}
