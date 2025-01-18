package com.example.recipefinder.data.model

import com.google.firebase.firestore.DocumentSnapshot

data class Tip(
    val timestamp: Long,
    val tip: String,
    val userName: String,
    val userProfileImageUrl: String
)

fun DocumentSnapshot.toTip(): Tip? {
    return try {
        Tip(
            timestamp = getLong("timestamp") ?: 0L,
            tip = getString("tip") ?: "",
            userName = getString("userName") ?: "",
            userProfileImageUrl = getString("userProfileImageUrl") ?: ""
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null // Return null if mapping fails
    }
}
