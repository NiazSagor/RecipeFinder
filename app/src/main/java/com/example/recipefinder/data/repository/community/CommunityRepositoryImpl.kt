package com.example.recipefinder.data.repository.community

import android.content.Context
import android.net.Uri
import com.example.recipefinder.data.model.CommunityPost
import com.example.recipefinder.data.model.toCommunityPost
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.resumable.MemoryResumableCache
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
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

    /*
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

    /*
    * post recipes that will appear on the community screen
    * */
    override suspend fun postRecipe(post: String, recipeTitle: String, recipeImageUri: Uri) {
        try {
            // TODO: get user name and profile image url
            val recipeImageUrl = uploadRecipePhoto(recipeTitle, recipeImageUri)
            val communityPost = CommunityPost(
                post = post,
                recipeTitle = recipeTitle,
                recipeImageUrl = recipeImageUrl!!,
                userName = "Ripa Akter",
                userProfileImageUrl = "",
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

    override suspend fun getCommunityPosts(): List<CommunityPost> {
        return try {
            val results = communityPostsDb
                .collection("community")
                .get()
                .await()
            results.documents.mapNotNull {
                it.toCommunityPost()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

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
