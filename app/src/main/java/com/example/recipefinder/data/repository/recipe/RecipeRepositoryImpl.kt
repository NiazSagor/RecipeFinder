package com.example.recipefinder.data.repository.recipe

import android.util.Log
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.model.RecipeAnalyzedInstructions
import com.example.recipefinder.data.model.RecipeNutrient
import com.example.recipefinder.data.model.SearchRecipeByIngredients
import com.example.recipefinder.data.model.Tip
import com.example.recipefinder.data.model.toRecipeAnalyzedInstructionsItemInternalModel
import com.example.recipefinder.data.model.toRecipeNutrientInternalModel
import com.example.recipefinder.data.repository.tip.RecipeTipsRepository
import com.example.recipefinder.datastore.RecipeDataStore
import com.example.recipefinder.model.SimilarRecipeItemVo
import com.example.recipefinder.model.toInternalRecipeModel
import com.example.recipefinder.model.toInternalRecipesModel
import com.example.recipefinder.model.toInternalSearchRecipesByIngredients
import com.example.recipefinder.network.RestApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val TAG = "HomeViewModel"

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDataStore: RecipeDataStore,
    private val restApiService: RestApiService,
    private val recipeTipsRepository: RecipeTipsRepository
) : RecipeRepository {

    override suspend fun getRandomRecipes(): Flow<List<Recipe>?> {
        return recipeDataStore.getRandomRecipes()
    }

    override suspend fun getSimilarRecipes(id: Int): List<SimilarRecipeItemVo> {
        return restApiService.getSimilarRecipes(id)
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
        return restApiService.getAnalyzedInstructions(id).first()
            .toRecipeAnalyzedInstructionsItemInternalModel()
    }

    override suspend fun saveRecipeInformation(recipe: Recipe) {
        try {
            val allRecipes = getRandomRecipes().first()?.toMutableList() ?: mutableListOf<Recipe>()
            Log.e(
                TAG,
                "saveRecipeInformation: recipe $recipe -------- allRecipes ${allRecipes.size}",
            )
            if (!allRecipes.contains(recipe)) {
                Log.e(TAG, "saveRecipeInformation: allRecipes not contain $recipe")
                allRecipes.add(recipe)
                recipeDataStore.saveRandomRecipes(allRecipes.toList())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getNutrients(id: Int): RecipeNutrient {
        return restApiService.getNutrients(id).toRecipeNutrientInternalModel()
    }

    override suspend fun searchDishType(type: String): List<Recipe> {
        return restApiService.searchRecipe(type, true, 10).results.toInternalRecipesModel()
    }

    override suspend fun sendTip(id: Int, tip: String) {
        recipeTipsRepository.sendTip(
            recipeId = id, tip = Tip(
                timestamp = System.currentTimeMillis(),
                tip = tip,
                userName = "Niaz Sagor",
                userProfileImageUrl = ""
            )
        )
    }

    override suspend fun like(id: Int) {

    }

    override suspend fun save(recipe: Recipe) {

    }

    override suspend fun getAllTipsForRecipe(recipeId: Int): List<Tip> {
        return recipeTipsRepository.getAllTipsForRecipe(recipeId)
    }
}
