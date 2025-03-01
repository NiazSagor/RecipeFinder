package com.example.recipefinder.data.repository.tip

import android.net.Uri
import com.example.recipefinder.data.model.Tip
import kotlinx.coroutines.flow.Flow

interface RecipeTipsRepository {
    fun getAllTipsForRecipe(recipeId: Int): Flow<List<Tip>>
    suspend fun getLikesForRecipe(recipeId: Int): Int
    suspend fun sendTip(recipeId: Int, tip: Tip, photoUri: Uri?)
    suspend fun like(recipeId: Int)
}
