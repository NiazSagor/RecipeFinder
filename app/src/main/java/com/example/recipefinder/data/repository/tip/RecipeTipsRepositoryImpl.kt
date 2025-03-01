package com.example.recipefinder.data.repository.tip

import android.content.Context
import android.net.Uri
import com.example.recipefinder.data.model.Tip
import com.example.recipefinder.data.model.toTip
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.firestore
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.resumable.MemoryResumableCache
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeTipsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : RecipeTipsRepository {

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

    private val tipsDb by lazy { Firebase.firestore }

    override fun getAllTipsForRecipe(recipeId: Int) = callbackFlow {
        val listener = tipsDb
            .collection("tips")
            .document(recipeId.toString())
            .collection("allTips")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                val tips = snapshot?.documents?.mapNotNull {
                    it.toTip()
                } ?: emptyList()

                trySend(tips)
            }

        awaitClose {
            listener.remove()
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun sendTip(
        recipeId: Int,
        tip: Tip,
        photoUri: Uri?
    ) {
        try {
//            if (photoUri != null) {
//                val downloadUrl = uploadRecipePhoto(tip.userName, photoUri)
//                tipsDb.collection("tips")
//                    .document(recipeId.toString())
//                    .collection("allTips")
//                    .add(tip.copy(photoUrl = downloadUrl))
//                    .await()
            //} else {
            tipsDb.collection("tips")
                .document(recipeId.toString())
                .collection("allTips")
                .add(tip)
                .await()
            //}
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
        }
    }

    override suspend fun like(recipeId: Int) {
        try {
            tipsDb
                .collection("recipes")
                .document(recipeId.toString())
                .update("like", FieldValue.increment(1))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getLikesForRecipe(recipeId: Int): Int {
        return try {
            val recipe = tipsDb
                .collection("recipes")
                .document(recipeId.toString())
                .get()
                .await()

            if (recipe.exists()) {
                recipe.getLong("like")?.toInt() ?: 0
            } else {
                tipsDb
                    .collection("recipes")
                    .document(recipeId.toString())
                    .set(mapOf<String, Int>(Pair("like", 0)))
                    .await()
                0
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }
    }

    private suspend fun uploadTipPhoto(
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
