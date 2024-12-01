package com.example.recipefinder.data.repository.recipe

import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.model.RecipeAnalyzedInstructions
import com.example.recipefinder.data.model.SearchRecipeByIngredients
import com.example.recipefinder.data.model.toRecipeAnalyzedInstructionsItemInternalModel
import com.example.recipefinder.datastore.RecipeDataStore
import com.example.recipefinder.model.toInternalRecipeModel
import com.example.recipefinder.model.toInternalSearchRecipesByIngredients
import com.example.recipefinder.network.RestApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeRepositoryImpl @Inject constructor(
    private val recipeDataStore: RecipeDataStore,
    private val restApiService: RestApiService,
) : RecipeRepository {

    override suspend fun getRandomRecipes(): Flow<List<Recipe>?> {
        return recipeDataStore.getRandomRecipes()
    }

    override suspend fun getRecipeById(id: Int): Recipe? {
        var recipe = recipeDataStore.getRecipeById(id)
        if (recipe == null) {
            recipe = restApiService.getRecipeInformation(id).toInternalRecipeModel()
        }
        return recipe
    }

    override suspend fun searchRecipesByIngredients(ingredients: String): List<SearchRecipeByIngredients> {
        return restApiService.findByIngredients(ingredients, 10)
            .toInternalSearchRecipesByIngredients()
    }

    override suspend fun getAnalyzedInstructions(id: Int): RecipeAnalyzedInstructions {
        return restApiService.getAnalyzedInstructions(id).first().toRecipeAnalyzedInstructionsItemInternalModel()
    }
}
