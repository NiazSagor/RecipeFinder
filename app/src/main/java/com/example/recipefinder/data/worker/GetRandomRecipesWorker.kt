package com.example.recipefinder.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.recipefinder.datastore.RecipeDataStore
import com.example.recipefinder.model.toInternalRecipesModel
import com.example.recipefinder.network.RestApiService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import java.lang.Exception
import java.time.Duration

@HiltWorker
class GetRandomRecipesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val recipeDataStore: RecipeDataStore,
    private val restApiService: RestApiService,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            supervisorScope {
                val randomRecipesDeferred = async { restApiService.getRandomRecipes(limit = 30) }
                val randomRecipes = randomRecipesDeferred.await()
                recipeDataStore.saveRandomRecipes(randomRecipes.recipes.toInternalRecipesModel())
                Result.success()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    companion object {
        fun enqueuePeriodicWork(context: Context) {
            val workRequest = PeriodicWorkRequestBuilder<GetRandomRecipesWorker>(
                repeatInterval = Duration.ofMinutes(20)
            ).build()
            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    "GetRandomRecipesWorker",
                    ExistingPeriodicWorkPolicy.KEEP,
                    workRequest
                )
        }

        fun enqueueOneTimeWork(context: Context) {
            val workRequest = OneTimeWorkRequestBuilder<GetRandomRecipesWorker>()
                .build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}
