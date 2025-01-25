package com.example.recipefinder.data.repository.tip

import android.net.Uri
import com.example.recipefinder.data.model.Tip
import com.example.recipefinder.data.model.toTip
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RecipeTipsRepositoryImpl @Inject constructor(
) : RecipeTipsRepository {

    private val tipsDb by lazy { Firebase.firestore }

    override suspend fun getAllTipsForRecipe(recipeId: Int): List<Tip> {
        return try {
            val result = tipsDb
                .collection("tips")
                .document(recipeId.toString())
                .collection("allTips")
                .get()
                .await()

            result.documents.mapNotNull { document ->
                document.toTip()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun sendTip(
        recipeId: Int,
        tip: Tip,
        photoUri: Uri?
    ) {
        try {
            if (photoUri != null) {
                // TODO: firebase storage is not available in the free tier
//                val downloadUrl =
//                    withContext(Dispatchers.IO) { uploadPhotoToFirebase(recipeId, photoUri) }
//
//                tipsDb.collection("tips")
//                    .document(recipeId.toString())
//                    .collection("allTips")
//                    .add(tip.copy(photoUrl = downloadUrl))
//                    .await()
            }

            tipsDb.collection("tips")
                .document(recipeId.toString())
                .collection("allTips")
                .add(tip)
                .await()
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
}
