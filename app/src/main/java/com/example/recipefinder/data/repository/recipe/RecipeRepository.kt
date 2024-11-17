package com.example.recipefinder.data.repository.recipe

import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.model.SearchRecipeByIngredients
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRandomRecipes(): Flow<List<Recipe>?>
    suspend fun getRecipeById(id: Int): Recipe?
    suspend fun searchRecipesByIngredients(ingredients: String): List<SearchRecipeByIngredients>
}
