package com.example.recipefinder.data.model

data class PostComment(
    val timestamp: Long = System.currentTimeMillis(),
    val comment: String,
    val userName: String,
    val userProfileImageUrl: String,
    val postId: String,
)
