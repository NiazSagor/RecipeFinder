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
import javax.inject.Inject
import javax.inject.Singleton

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

    suspend fun getRandomRecipes(): Flow<List<Recipe>?> {
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

    suspend fun getRecipeById(id: Int): Recipe? {
        val savedRecipes: List<Recipe>? = getRandomRecipes().first()
        return savedRecipes?.let {
            it.firstOrNull { it.id == id }
        }
    }

    suspend fun getBookmarkedRecipes(): List<Recipe> {
        return context.recipeDataStore
            .data
            .catch { emit(emptyPreferences()) }
            .map { preferences ->
                val jsonString = preferences[stringPreferencesKey("bookmarked_recipes")] ?: ""
                if (jsonString.isNotEmpty()) {
                    val type = object : TypeToken<List<Recipe>>() {}.type
                    Gson().fromJson<List<Recipe>>(jsonString, type) ?: emptyList()
                } else {
                    emptyList()
                }
            }.first()
    }

    suspend fun bookmarkRecipe(recipe: Recipe) {
        val bookmarkedRecipes: List<Recipe> = getBookmarkedRecipes()
        if (bookmarkedRecipes.contains(recipe) == false) {
            context.recipeDataStore
                .edit { preferences ->
                    val jsonString = gson.toJson(bookmarkedRecipes.plus(recipe))
                    preferences[stringPreferencesKey("bookmarked_recipes")] = jsonString
                }
        }
    }
}
