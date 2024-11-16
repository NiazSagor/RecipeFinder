package com.example.recipefinder.data.repository.recipe

import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.datastore.RecipeDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeRepositoryImpl @Inject constructor(
    private val recipeDataStore: RecipeDataStore,
) : RecipeRepository {

    override suspend fun getRandomRecipes(): Flow<List<Recipe>?> {
        return recipeDataStore.getRandomRecipes()
    }

    override suspend fun getRecipeById(id: Int): Recipe? {
        return recipeDataStore.getRecipeById(id)
    }
}
