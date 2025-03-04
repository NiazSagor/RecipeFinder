package com.example.recipefinder.data.repository.tip

import android.net.Uri
import com.example.recipefinder.data.model.Tip
import com.example.recipefinder.data.model.toTip
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RecipeTipsRepositoryImpl @Inject constructor(
    private val tipsDb: FirebaseFirestore,
) : RecipeTipsRepository {

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
}
