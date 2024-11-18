package com.example.recipefinder.data.repository.recipe

import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.model.SearchRecipeByIngredients
import com.example.recipefinder.datastore.RecipeDataStore
import com.example.recipefinder.model.toInternalSearchRecipesByIngredients
import com.example.recipefinder.network.RestApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeRepositoryImpl @Inject constructor(
    private val recipeDataStore: RecipeDataStore,
    private val restApiService: RestApiService,
) : RecipeRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override suspend fun getRandomRecipes(): Flow<List<Recipe>?> {
        return recipeDataStore.getRandomRecipes()
    }

    override suspend fun getRecipeById(id: Int): Recipe? {
        return recipeDataStore.getRecipeById(id)
    }

    override suspend fun searchRecipesByIngredients(ingredients: String): List<SearchRecipeByIngredients> {
        return restApiService.findByIngredients(ingredients, 10)
            .toInternalSearchRecipesByIngredients()
    }
}
