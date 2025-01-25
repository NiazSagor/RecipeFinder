package com.example.recipefinder.data.repository.tip

import android.net.Uri
import com.example.recipefinder.data.model.Tip

interface RecipeTipsRepository {
    suspend fun getAllTipsForRecipe(recipeId: Int): List<Tip>
    suspend fun getLikesForRecipe(recipeId: Int): Int
    suspend fun sendTip(recipeId: Int, tip: Tip, photoUri: Uri?)
    suspend fun like(recipeId: Int)
}
