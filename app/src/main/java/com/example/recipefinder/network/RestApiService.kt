package com.example.recipefinder.network


import com.example.recipefinder.model.RandomRecipesVo
import com.example.recipefinder.network.annotation.Format
import com.example.recipefinder.network.annotation.ResponseFormat
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApiService {

    @ResponseFormat(Format.JSON)
    @GET("/recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") limit: Int
    ): RandomRecipesVo
}
