package com.example.recipefinder.data.model

import com.google.firebase.firestore.DocumentSnapshot
import java.util.UUID

data class CommunityPost(
    val timestamp: Long = System.currentTimeMillis(),
    val post: String,
    val userName: String,
    val userProfileImageUrl: String,
    val recipeImageUrl: String,
    val recipeTitle: String,
    val like: Int,
    val postId: String = UUID.randomUUID().toString()
)

fun DocumentSnapshot.toCommunityPost(): CommunityPost? {
    return try {
        CommunityPost(
            timestamp = getLong("timestamp") ?: 0L,
            post = getString("post") ?: "",
            userName = getString("userName") ?: "",
            userProfileImageUrl = getString("userProfileImageUrl") ?: "",
            recipeImageUrl = getString("recipeImageUrl") ?: "",
            recipeTitle = getString("recipeTitle") ?: "",
            like = getLong("like")?.toInt() ?: 0,
            postId = getString("postId") ?: "",
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
