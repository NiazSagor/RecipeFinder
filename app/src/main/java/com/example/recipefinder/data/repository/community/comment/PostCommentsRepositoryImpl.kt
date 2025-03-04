package com.example.recipefinder.data.repository.community.comment

import com.example.recipefinder.data.model.PostComment
import com.example.recipefinder.data.model.toPostComment
import com.example.recipefinder.data.repository.user.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostCommentsRepositoryImpl @Inject constructor(
    private val userRepository: UserRepository
) : PostCommentsRepository {

    private val postCommentsDb by lazy { Firebase.firestore }

    override fun getPostComments(postId: String) = callbackFlow {
        val listener = postCommentsDb
            .collection("community_post_comments")
            .document(postId)
            .collection("allComments")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                val comments = snapshot?.documents?.mapNotNull {
                    it.toPostComment()
                } ?: emptyList()
                trySend(comments)
            }

        awaitClose(
            listener::remove
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun postComment(postId: String, comment: String) {
        val comment = PostComment(
            comment = comment,
            userName = userRepository.getName(),
            userProfileImageUrl = userRepository.getPhoto().toString(),
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
