package com.example.recipefinder.data.repository.recipe

import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.model.RecipeAnalyzedInstructions
import com.example.recipefinder.data.model.RecipeNutrient
import com.example.recipefinder.data.model.SearchRecipeByIngredients
import com.example.recipefinder.data.model.Tip
import com.example.recipefinder.data.model.toInternalRecipeAnalyzedInstructionsItem
import com.example.recipefinder.data.model.toRecipeNutrientInternalModel
import com.example.recipefinder.data.repository.tip.RecipeTipsRepository
import com.example.recipefinder.data.repository.user.UserRepository
import com.example.recipefinder.datastore.RecipeDataStore
import com.example.recipefinder.model.RecipeAnalyzedInstructionsItemVo
import com.example.recipefinder.model.RecipeNutrientsVo
import com.example.recipefinder.model.SearchRecipeByIngredientsResponseVo
import com.example.recipefinder.model.SearchRecipesVo
import com.example.recipefinder.model.toInternalRecipeModel
import com.example.recipefinder.model.toInternalRecipesModel
import com.example.recipefinder.model.toInternalSearchRecipesByIngredients
import com.example.recipefinder.network.RestApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDataStore: RecipeDataStore,
    private val restApiService: RestApiService,
    private val recipeTipsRepository: RecipeTipsRepository,
    private val userRepository: UserRepository,
) : RecipeRepository {

    override fun getRandomRecipes(): Flow<List<Recipe>> {
        return recipeDataStore.getSavedRecipes()
    }

    override suspend fun getRecipeDetail(recipeId: Int): Recipe? {
        var recipe: Recipe? = recipeDataStore.getRecipeDetail(recipeId) // search in local first
        if (recipe == null) {
            recipe =
                restApiService.getRecipeDetail(recipeId).toInternalRecipeModel() // get from api
        }
        return recipe
    }

    override suspend fun searchRecipesByIngredients(ingredients: String): List<SearchRecipeByIngredients> {
        val response: List<SearchRecipeByIngredientsResponseVo> =
            restApiService.findByIngredients(ingredients = ingredients, number = 10)
        return response.toInternalSearchRecipesByIngredients()
    }

    override suspend fun getAnalyzedInstructions(recipeId: Int): RecipeAnalyzedInstructions {
        val response: RecipeAnalyzedInstructionsItemVo =
            restApiService.getAnalyzedInstructions(id = recipeId).first()
        return response.toInternalRecipeAnalyzedInstructionsItem()
    }

    override suspend fun addNewRecipe(recipe: Recipe) {
        try {
            val allRecipes: MutableList<Recipe> =
                recipeDataStore.getSavedRecipes().first().toMutableList()
            if (!allRecipes.contains(recipe)) {
                allRecipes.add(recipe)
                recipeDataStore.updateAllRecipes(allRecipes.toList())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getNutrients(recipeId: Int): RecipeNutrient {
        val response: RecipeNutrientsVo = restApiService.getNutrients(recipeId)
        return response.toRecipeNutrientInternalModel()
    }

    override suspend fun searchMealType(
        query: String,
        type: String,
        maxReadyTime: Int,
    ): List<Recipe> {
        val searchRecipesVo: SearchRecipesVo = restApiService.searchRecipe(
            query = query,
            type = type,
            maxReadyTime = maxReadyTime,
            includeIngredients = query
        )
        return searchRecipesVo.results.toInternalRecipesModel()
    }

    override suspend fun sendTip(recipeId: Int, tip: String) {
        val tip = Tip(
            timestamp = System.currentTimeMillis(),
            description = tip,
            userName = userRepository.getName(),
            userProfileImageUrl = userRepository.getPhoto().toString()
        )
        recipeTipsRepository.sendTip(
            recipeId = recipeId,
            tip = tip
        )
    }

    override suspend fun like(recipeId: Int) {
        recipeTipsRepository.like(recipeId)
    }

    override suspend fun getLikesForRecipes(recipeId: Int): Int {
        return recipeTipsRepository.getLikesForRecipe(recipeId)
    }

    override suspend fun save(recipe: Recipe) {
        recipeDataStore.bookmarkRecipe(recipe)
    }
}
