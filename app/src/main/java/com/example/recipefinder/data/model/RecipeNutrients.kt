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
        calorieCount = this.calorieCount,
        carbohydrates = this.carbohydrates,
        fats = this.fats,
        proteins = this.proteins
    )
}
