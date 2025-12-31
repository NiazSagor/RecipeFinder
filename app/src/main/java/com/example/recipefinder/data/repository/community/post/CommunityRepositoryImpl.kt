package com.example.recipefinder.data.repository.community.post

import android.content.Context
import android.net.Uri
import com.example.recipefinder.data.model.CommunityPost
import com.example.recipefinder.data.model.toCommunityPost
import com.example.recipefinder.data.repository.community.like.CommunityPostLikeRepository
import com.example.recipefinder.data.repository.user.UserRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseFirestore: FirebaseFirestore,
    private val supabase: SupabaseClient,
    private val userRepository: UserRepository,
    private val communityPostLikeRepository: CommunityPostLikeRepository,
) : CommunityRepository {

    override suspend fun likePost(postId: String) {
        communityPostLikeRepository.likePost(postId)
    }

    override fun getPost(postId: String): Flow<CommunityPost?> = flow {
        try {
            val result: DocumentSnapshot? = firebaseFirestore
                .collection("community")
                .document(postId)
                .get()
                .await()
            emit(result?.toCommunityPost())
        } catch (e: Exception) {
            emit(null)
        }
    }

    override suspend fun isPostLikedByUser(postId: String): Boolean {
        return communityPostLikeRepository.isPostLikedByUser(postId)
    }

    override suspend fun postRecipe(post: String, recipeTitle: String, recipeImageUri: Uri) {
        try {
            val recipeImageUrl = uploadRecipePhoto(recipeTitle, recipeImageUri)
            val communityPost = CommunityPost(
                post = post,
                recipeTitle = recipeTitle,
                recipeImageUrl = recipeImageUrl,
                userName = userRepository.getName(),
                userProfileImageUrl = userRepository.getPhoto().toString(),
                like = 0
            )
            firebaseFirestore
                .collection("community")
                .document(communityPost.postId)
                .set(communityPost)
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to post recipe")
        }
    }

    /**
     * todo: add pagination
     * */
    override fun getCommunityPosts(): Flow<List<CommunityPost>> = callbackFlow {
        val listener =
            firebaseFirestore
                .collection("community")
                .addSnapshotListener { snapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val posts: List<CommunityPost> =
                        snapshot?.documents?.mapNotNull { documentSnapshot ->
                            documentSnapshot.toCommunityPost()
                    } ?: emptyList()
                    trySend(posts)
                }
        awaitClose(
            listener::remove
        )
    }.flowOn(Dispatchers.IO)

    private suspend fun uploadRecipePhoto(
        title: String,
        imageUri: Uri
    ): String {
        return withContext(Dispatchers.IO) {
            val inputStream =
                context.contentResolver.openInputStream(imageUri)
                    ?: throw Exception("Recipe image URI is invalid")
            val byteArray = inputStream.readBytes()
            val safeTitle = title.replace(" ", "_").replace(Regex("[^A-Za-z0-9_]"), "")
            val fileName = "public/$safeTitle.jpg"
            supabase.storage.from("RecipeFinder").upload(path = fileName, data = byteArray)
            supabase.storage.from("RecipeFinder").publicUrl(fileName)
        }
    }
}
