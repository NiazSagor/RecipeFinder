package com.example.recipefinder.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.recipefinder.data.model.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "RecipeDataStore"

@Singleton
class RecipeDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val gson: Gson = Gson()

    private val Context.recipeDataStore:
            DataStore<Preferences> by preferencesDataStore(
        name = "recipe"
    )

    suspend fun saveRandomRecipes(
        randomRecipes: List<Recipe>
    ) {
        val savedRecipes: List<Recipe> = getRandomRecipes().first() ?: emptyList()
        val newRecipes = randomRecipes.filter {
            savedRecipes.contains(it) == false
        }
        context.recipeDataStore
            .edit { preferences ->
                val jsonString = gson.toJson(savedRecipes.plus(newRecipes))
                preferences[stringPreferencesKey("random_recipes")] = jsonString
            }
    }

    fun getRandomRecipes(): Flow<List<Recipe>?> {
        return context.recipeDataStore
            .data
            .catch { emit(emptyPreferences()) }
            .map { preferences ->
                val jsonString = preferences[stringPreferencesKey("random_recipes")] ?: ""
                if (jsonString.isNotEmpty()) {
                    val type = object : TypeToken<List<Recipe>>() {}.type
                    gson.fromJson(jsonString, type)
                } else {
                    emptyList()
                }
            }
    }

    suspend fun likeRecipe(recipe: Recipe) {
        val likedRecipes: List<Recipe> = getLikedRecipes().first() ?: emptyList()
        if (!likedRecipes.contains(recipe)) {
            context.recipeDataStore
                .edit { preferences ->
                    val jsonString = gson.toJson(likedRecipes.plus(recipe))
                    preferences[stringPreferencesKey("liked_recipes")] = jsonString
                }
        }
    }

    fun getLikedRecipes(): Flow<List<Recipe>?> {
        return context.recipeDataStore
            .data
            .catch { emit(emptyPreferences()) }
            .map { preferences ->
                val jsonString = preferences[stringPreferencesKey("liked_recipes")] ?: ""
                if (jsonString.isNotEmpty()) {
                    val type = object : TypeToken<List<Recipe>>() {}.type
                    gson.fromJson(jsonString, type)
                } else {
                    emptyList()
                }
            }
    }

    suspend fun tippedRecipe(recipe: Recipe) {
        val likedRecipes: List<Recipe> = getTippedRecipes().first() ?: emptyList()
        if (!likedRecipes.contains(recipe)) {
            context.recipeDataStore
                .edit { preferences ->
                    val jsonString = gson.toJson(likedRecipes.plus(recipe))
                    preferences[stringPreferencesKey("tipped_recipes")] = jsonString
                }
        }
    }

    fun getTippedRecipes(): Flow<List<Recipe>?> {
        return context.recipeDataStore
            .data
            .catch { emit(emptyPreferences()) }
            .map { preferences ->
                val jsonString = preferences[stringPreferencesKey("tipped_recipes")] ?: ""
                if (jsonString.isNotEmpty()) {
                    val type = object : TypeToken<List<Recipe>>() {}.type
                    gson.fromJson(jsonString, type)
                } else {
                    emptyList()
                }
            }
    }

    suspend fun getRecipeDetail(id: Int): Recipe? {
        val savedRecipes: List<Recipe>? = getRandomRecipes().first()
        return savedRecipes?.let {
            it.firstOrNull { it.id == id }
        }
    }

    suspend fun getBookmarkedRecipes(): List<Recipe> {
        return getRandomRecipes().first()?.filter { it.isBookmarked } ?: emptyList()
    }

    suspend fun bookmarkRecipe(recipe: Recipe) {
        val allRecipes: List<Recipe>? = getRandomRecipes().first()

        if (allRecipes == null || allRecipes.none { it.id == recipe.id }) {
            Log.e(TAG, "bookmarkRecipe: Recipe not found in the list")
        } else {
            val updatedRecipes = allRecipes.map { currentRecipe: Recipe ->
                if (currentRecipe.id == recipe.id) {
                    // Update the `isBookmarked` field for the specific recipe
                    if (currentRecipe.isBookmarked == true) {
                        // if already true
                        currentRecipe.copy(isBookmarked = false)
                    } else {
                        currentRecipe.copy(isBookmarked = true)
                    }
                } else {
                    currentRecipe
                }
            }

            updateAllRecipes(updatedRecipes)
        }
    }

    suspend fun updateAllRecipes(allRecipes: List<Recipe>) {
        context.recipeDataStore.edit { preferences ->
            val updatedRecipeJson = gson.toJson(allRecipes)
            preferences[stringPreferencesKey("random_recipes")] = updatedRecipeJson
        }
    }
}
