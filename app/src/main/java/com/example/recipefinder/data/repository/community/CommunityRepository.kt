package com.example.recipefinder.data.repository.community

import android.net.Uri
import com.example.recipefinder.data.model.CommunityPost

interface CommunityRepository {
    suspend fun postRecipe(post: String, recipeTitle: String, recipeImageUri: Uri)
    suspend fun getCommunityPosts(): List<CommunityPost>
}
