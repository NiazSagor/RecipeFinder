package com.example.recipefinder.data.repository.tip

import android.util.Log
import com.example.recipefinder.data.model.Tip
import com.example.recipefinder.data.model.toTip
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "RecipeTipsRepositoryImp"

class RecipeTipsRepositoryImpl @Inject constructor() : RecipeTipsRepository {

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
                Log.e(TAG, "getAllTipsForRecipe: tip = ${document.get("tip").toString()}")
                document.toTip()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun sendTip(
        recipeId: Int,
        tip: Tip
    ) {
        try {
            tipsDb.collection("tips")
                .document(recipeId.toString())
                .collection("allTips")
                .add(tip)
                .await() // Await the Task to complete
        } catch (e: FirebaseFirestoreException) {
            // Handle any errors
            e.printStackTrace()
        }
    }
}
