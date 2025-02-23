package com.example.recipefinder.network

import com.example.recipefinder.model.RandomRecipesVo
import com.example.recipefinder.model.RecipeAnalyzedInstructionsItemVo
import com.example.recipefinder.model.RecipeInformationVo
import com.example.recipefinder.model.RecipeNutrientsVo
import com.example.recipefinder.model.SearchRecipeByIngredientsResponseVo
import com.example.recipefinder.model.SearchRecipesVo
import com.example.recipefinder.model.SimilarRecipeItemVo
import com.example.recipefinder.network.annotation.Format
import com.example.recipefinder.network.annotation.ResponseFormat
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApiService {

    @ResponseFormat(Format.JSON)
    @GET("/recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") limit: Int
    ): RandomRecipesVo

    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(
        @Path("id") id: Int,
    ): RecipeInformationVo

    @GET("recipes/{id}/analyzedInstructions")
    suspend fun getAnalyzedInstructions(
        @Path("id") id: Int,
    ): List<RecipeAnalyzedInstructionsItemVo>

    @ResponseFormat(Format.JSON)
    @GET("/recipes/findByIngredients")
    suspend fun findByIngredients(
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int,
        @Query("limitLicense") limitLicense: Boolean = true,
        @Query("ranking") ranking: Int = 1,
        @Query("ignorePantry") ignorePantry: Boolean = false
    ): List<SearchRecipeByIngredientsResponseVo>

    @GET("recipes/{id}/similar")
    suspend fun getSimilarRecipes(
        @Path("id") id: Int,
    ): List<SimilarRecipeItemVo>

    @ResponseFormat(Format.JSON)
    @GET("recipes/{id}/nutritionWidget.json")
    suspend fun getNutrients(
        @Path("id") id: Int,
    ): RecipeNutrientsVo

    @ResponseFormat(Format.JSON)
    @GET("recipes/complexSearch")
    suspend fun searchRecipe(
        @Query("query") query: String,
        @Query("type") type: String,
        @Query("maxReadyTime") maxReadyTime: Int,
        @Query("addRecipeInformation") addRecipeInformation: Boolean,
        @Query("includeIngredients") includeIngredients: String,
        @Query("number") number: Int,
    ): SearchRecipesVo
}
