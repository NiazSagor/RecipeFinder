package com.example.recipefinder.data.repository.community

import android.net.Uri

interface CommunityRepository {
    suspend fun postRecipe(post: String, recipeTitle: String, recipeImageUri: Uri)
}
