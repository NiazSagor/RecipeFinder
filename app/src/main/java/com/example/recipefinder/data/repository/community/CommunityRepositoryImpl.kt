package com.example.recipefinder.data.repository.community

import android.content.Context
import android.net.Uri
import com.example.recipefinder.data.model.CommunityPost
import com.example.recipefinder.data.model.toCommunityPost
import com.example.recipefinder.data.repository.user.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.resumable.MemoryResumableCache
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRepository: UserRepository
) : CommunityRepository {

    // TODO: move the url and key to secrets
    private val supabase by lazy {
        createSupabaseClient(
            supabaseUrl = "https://kxspxycwnuemptjvvgbk.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imt4c3B4eWN3bnVlbXB0anZ2Z2JrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzgxNjE3MjYsImV4cCI6MjA1MzczNzcyNn0.f12nkXNmnRtur4vay2MjZhIuffK31KeNc85jg79RRCY"
        ) {
            install(Storage) {
                resumable {
                    cache = MemoryResumableCache()
                }
            }
        }
    }

    private val communityPostsDb by lazy { Firebase.firestore }

    /**
     * like a post in the community screen
     * */
    override suspend fun likePost(postId: String) {
        try {
            communityPostsDb
                .collection("community")
                .document(postId)
                .update("like", FieldValue.increment(1))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * get a particular recipe post by id
     * this is required when a user want to comment on a recipe post
     * */
    override suspend fun getPost(postId: String): CommunityPost? {
        return try {
            val result = communityPostsDb
                .collection("community")
                .document(postId)
                .get()
                .await()
            result?.toCommunityPost()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * post recipes that will appear on the community screen
     * */
    override suspend fun postRecipe(post: String, recipeTitle: String, recipeImageUri: Uri) {
        try {
            val recipeImageUrl = uploadRecipePhoto(recipeTitle, recipeImageUri)
            val communityPost = CommunityPost(
                post = post,
                recipeTitle = recipeTitle,
                recipeImageUrl = recipeImageUrl!!,
                userName = userRepository.getName(),
                userProfileImageUrl = userRepository.getPhoto().toString(),
                like = 0
            )
            communityPostsDb
                .collection("community")
                .document(communityPost.postId)
                .set(communityPost)
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to post recipe")
        }
    }

    /**
     * reactively returns the posts
     * as the post can have likes count that needs to be
     * updated on the screen as the user likes the post
     * todo: add pagination
     * */
    override fun getCommunityPosts(): Flow<List<CommunityPost>> = callbackFlow {
        val listener =
            communityPostsDb
                .collection("community")
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        close(e)
                        return@addSnapshotListener
                    }
                    val posts = snapshot?.documents?.mapNotNull {
                        it.toCommunityPost()
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
    ): String? {
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
