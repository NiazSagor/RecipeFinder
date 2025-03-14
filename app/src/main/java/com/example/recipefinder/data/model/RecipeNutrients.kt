package com.example.recipefinder.data.model

import com.example.recipefinder.model.RecipeNutrientsVo

data class RecipeNutrient(
    val calorieCount: String,
    val carbohydrates: String,
    val fats: String,
    val proteins: String
)

fun RecipeNutrientsVo.toRecipeNutrientInternalModel(): RecipeNutrient {
    return RecipeNutrient(
        calorieCount = this.calories ?: "",
        carbohydrates = this.carbs ?: "",
        fats = this.fat ?: "",
        proteins = this.protein ?: ""
    )
}
