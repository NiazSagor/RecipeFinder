package com.example.recipefinder.data.repository.community

import com.example.recipefinder.data.model.PostComment
import com.example.recipefinder.data.model.toPostComment
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostCommentsRepositoryImpl @Inject constructor() : PostCommentsRepository {

    private val postCommentsDb by lazy { Firebase.firestore }

    override suspend fun getPostComments(postId: String): List<PostComment> {
        return try {
            val result = postCommentsDb
                .collection("community_post_comments")
                .document(postId)
                .collection("allComments")
                .get()
                .await()

            result.documents.mapNotNull { document ->
                document.toPostComment()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun postComment(postId: String, comment: String) {
        // TODO: get user name and profile image
        val comment = PostComment(
            comment = comment,
            userName = "Niaz Sagor",
            userProfileImageUrl = "",
            postId = postId
        )
        postCommentsDb
            .collection("community_post_comments")
            .document(postId)
            .collection("allComments")
            .add(comment)
            .await()
    }
}
