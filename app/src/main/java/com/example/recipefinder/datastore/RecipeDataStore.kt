package com.example.recipefinder.datastore

import android.content.Context
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
import java.lang.reflect.Type
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

    suspend fun saveRecipes(
        recipes: List<Recipe>
    ) {
        val savedRecipes: List<Recipe> = getSavedRecipes().first()
        val newRecipes: List<Recipe> = recipes.filter { recipe: Recipe ->
            savedRecipes.contains(recipe) == false
        }
        context.recipeDataStore
            .edit { preferences ->
                val newUpdatedRecipeList: List<Recipe> = savedRecipes.plus(newRecipes)
                val jsonString = gson.toJson(newUpdatedRecipeList)
                preferences[stringPreferencesKey("saved_recipes")] = jsonString
            }
    }

    fun getSavedRecipes(): Flow<List<Recipe>> {
        return context.recipeDataStore
            .data
            .catch { emit(emptyPreferences()) }
            .map { preferences ->
                val jsonString = preferences[stringPreferencesKey("saved_recipes")] ?: ""
                if (jsonString.isNotEmpty()) {
                    val type: Type? = object : TypeToken<List<Recipe>>() {}.type
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
        val savedRecipes: List<Recipe> = getSavedRecipes().first()
        return savedRecipes.firstOrNull { recipe: Recipe -> recipe.id == id }
    }

    suspend fun bookmarkRecipe(recipe: Recipe) {
        val savedRecipes: List<Recipe> = getSavedRecipes().first()
        val updatedRecipes = savedRecipes.map { currentRecipe: Recipe ->
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

    suspend fun updateAllRecipes(allRecipes: List<Recipe>) {
        context.recipeDataStore.edit { preferences ->
            val updatedRecipeJson = gson.toJson(allRecipes)
            preferences[stringPreferencesKey("saved_recipes")] = updatedRecipeJson
        }
    }
}
