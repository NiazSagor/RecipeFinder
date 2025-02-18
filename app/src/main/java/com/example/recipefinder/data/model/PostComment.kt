package com.example.recipefinder.data.model

import com.google.firebase.firestore.DocumentSnapshot

data class PostComment(
    val timestamp: Long = System.currentTimeMillis(),
    val comment: String,
    val userName: String,
    val userProfileImageUrl: String,
    val postId: String,
)


fun DocumentSnapshot.toPostComment(): PostComment? {
    return try {
        PostComment(
            timestamp = getLong("timestamp") ?: 0L,
            comment = getString("comment") ?: "",
            userName = getString("userName") ?: "",
            userProfileImageUrl = getString("userProfileImageUrl") ?: "",
            postId = getString("postId") ?: ""
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
