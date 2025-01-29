package com.example.recipefinder.data.repository.community

import android.content.Context
import android.net.Uri
import com.example.recipefinder.data.model.CommunityPost
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : CommunityRepository {

    private val supabase by lazy {
        createSupabaseClient(
            supabaseUrl = "https://kxspxycwnuemptjvvgbk.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imt4c3B4eWN3bnVlbXB0anZ2Z2JrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzgxNjE3MjYsImV4cCI6MjA1MzczNzcyNn0.f12nkXNmnRtur4vay2MjZhIuffK31KeNc85jg79RRCY"
        ) {
            install(Storage)
        }
    }

    private val communityPostsDb by lazy { Firebase.firestore }

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
                .add(communityPost)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun uploadRecipePhoto(
        title: String,
        imageUri: Uri
    ): String? {
        return withContext(Dispatchers.IO) {
            val inputStream =
                context.contentResolver.openInputStream(imageUri) ?: throw Exception("Recipe image URI is invalid")
            val byteArray = inputStream.readBytes()
            val fileName = "community_recipes/posts_images/$title.jpg"
            supabase.storage.from("RecipeFinder").upload(fileName, byteArray)
            supabase.storage.from("RecipeFinder").publicUrl(fileName)
        }
    }
}
