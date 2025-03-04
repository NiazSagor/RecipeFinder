package com.example.recipefinder.data.repository.community.like

import com.example.recipefinder.data.repository.user.UserRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CommunityPostLikeRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val userRepository: UserRepository
) : CommunityPostLikeRepository {

    override suspend fun isPostLikedByUser(postId: String): Boolean {
        return isCommunityPostLikedByUser(postId)
    }

    override suspend fun likePost(postId: String) {
        try {
            val isPostAlreadyLikedByUser = isCommunityPostLikedByUser(postId)
            if (isPostAlreadyLikedByUser) {
                removeCommunityPostIdFromLikedCommunityPostsCollection(postId)
                decrementCommunityPostLikeCount(postId)
            } else {
                addCommunityPostIdIntoLikedCommunityPostsCollection(postId)
                incrementCommunityPostLikeCount(postId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun incrementCommunityPostLikeCount(
        postId: String,
    ) {
        firebaseFirestore
            .collection("community")
            .document(postId)
            .update("like", FieldValue.increment(1))
    }

    private fun decrementCommunityPostLikeCount(
        postId: String,
    ) {
        firebaseFirestore
            .collection("community")
            .document(postId)
            .update("like", FieldValue.increment(-1))
    }

    private suspend fun isCommunityPostLikedByUser(
        postId: String
    ): Boolean {
        val email = userRepository.getEmail()
        val document: DocumentSnapshot? = firebaseFirestore
            .collection("users")
            .document(email)
            .collection("likedCommunityPosts")
            .document(postId)
            .get()
            .await()
        return document?.exists() == true
    }

    private fun addCommunityPostIdIntoLikedCommunityPostsCollection(
        postId: String
    ) {
        val email = userRepository.getEmail()
        firebaseFirestore
            .collection("users")
            .document(email)
            .collection("likedCommunityPosts")
            .document(postId)
            .set(emptyMap<String, Any>())
    }

    private fun removeCommunityPostIdFromLikedCommunityPostsCollection(
        postId: String
    ) {
        val email = userRepository.getEmail()
        firebaseFirestore
            .collection("users")
            .document(email)
            .collection("likedCommunityPosts")
            .document(postId)
            .delete()
    }
}
