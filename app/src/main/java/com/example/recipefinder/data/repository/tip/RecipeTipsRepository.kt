package com.example.recipefinder.data.repository.tip

import com.example.recipefinder.data.model.Tip

interface RecipeTipsRepository {
    suspend fun getAllTipsForRecipe(recipeId: Int): List<Tip>
    suspend fun sendTip(recipeId: Int, tip: Tip)
}
