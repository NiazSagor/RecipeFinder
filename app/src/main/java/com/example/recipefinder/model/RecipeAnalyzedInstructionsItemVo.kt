package com.example.recipefinder.model

// external
data class RecipeAnalyzedInstructionsItemVo(
    val name: String,
    val steps: List<StepVo>
)

data class StepVo(
    val equipment: List<EquipmentVo>,
    val ingredients: List<IngredientVo>,
    val length: LengthVo,
    val number: Int,
    val step: String
)

data class LengthVo(
    val number: Int,
    val unit: String
)

data class EquipmentVo(
    val id: Int,
    val image: String,
    val name: String,
    val temperature: TemperatureVo
)

data class IngredientVo(
    val id: Int,
    val image: String,
    val name: String
)

data class TemperatureVo(
    val number: Double,
    val unit: String
)
