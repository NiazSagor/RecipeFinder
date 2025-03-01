package com.example.recipefinder.data.repository.community

import android.net.Uri
import com.example.recipefinder.data.model.CommunityPost
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    fun getCommunityPosts(): Flow<List<CommunityPost>>
    fun getPost(postId: String): Flow<CommunityPost?>
    suspend fun likePost(postId: String)
    suspend fun postRecipe(post: String, recipeTitle: String, recipeImageUri: Uri)
}
