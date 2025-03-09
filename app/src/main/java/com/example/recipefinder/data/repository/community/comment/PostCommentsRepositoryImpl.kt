package com.example.recipefinder.data.repository.community.comment

import com.example.recipefinder.data.model.PostComment
import com.example.recipefinder.data.model.toPostComment
import com.example.recipefinder.data.repository.user.UserRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostCommentsRepositoryImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseFirestore: FirebaseFirestore,
) : PostCommentsRepository {

    override fun getPostComments(postId: String): Flow<List<PostComment>> = callbackFlow {
        val listener = firebaseFirestore
            .collection("community_post_comments")
            .document(postId)
            .collection("allComments")
            .addSnapshotListener { snapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val comments: List<PostComment> =
                    snapshot?.documents?.mapNotNull { documentSnapshot: DocumentSnapshot? ->
                        documentSnapshot?.toPostComment()
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
        firebaseFirestore
            .collection("community_post_comments")
            .document(postId)
            .collection("allComments")
            .add(comment)
            .await()
    }
}
