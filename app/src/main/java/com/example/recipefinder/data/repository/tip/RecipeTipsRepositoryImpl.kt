package com.example.recipefinder.data.repository.tip

import com.example.recipefinder.data.model.Tip
import com.example.recipefinder.data.model.toTip
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RecipeTipsRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : RecipeTipsRepository {

    override fun getAllTipsForRecipe(recipeId: Int): Flow<List<Tip>> = callbackFlow {
        val listener = firebaseFirestore
            .collection("tips")
            .document(recipeId.toString())
            .collection("allTips")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                val tips: List<Tip> = snapshot?.documents?.mapNotNull {
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
    ) {
        try {
            firebaseFirestore.collection("tips")
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
            firebaseFirestore
                .collection("recipes")
                .document(recipeId.toString())
                .update("like", FieldValue.increment(1))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getLikesForRecipe(recipeId: Int): Int {
        return try {
            val recipe: DocumentSnapshot? =
                firebaseFirestore
                    .collection("recipes")
                    .document(recipeId.toString())
                    .get()
                    .await()

            if (recipe?.exists() == true) {
                recipe.getLong("like")?.toInt() ?: 0
            } else {
                firebaseFirestore
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
