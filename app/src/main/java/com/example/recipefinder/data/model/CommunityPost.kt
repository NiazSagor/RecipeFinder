package com.example.recipefinder.data.model

import java.util.UUID

data class CommunityPost(
    val timestamp: Long,
    val post: String,
    val userName: String,
    val userProfileImageUrl: String,
    val recipeImageUrl: String,
    val recipeTitle: String,
    val like: Int,
    val postId: String = UUID.randomUUID().toString()
)
