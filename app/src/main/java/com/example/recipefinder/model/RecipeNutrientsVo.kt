package com.example.recipefinder.model

import com.google.gson.annotations.SerializedName

data class RecipeNutrientsVo(
    @SerializedName("calories") val calorieCount: String,
    @SerializedName("carbs") val carbohydrates: String,
    @SerializedName("fat") val fats: String,
    @SerializedName("protein") val proteins: String
)
