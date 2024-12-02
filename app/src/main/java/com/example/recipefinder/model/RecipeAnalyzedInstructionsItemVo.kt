package com.example.recipefinder.model

import com.google.gson.annotations.SerializedName

data class RecipeAnalyzedInstructionsItemVo(
    @SerializedName("name")
    val name: String,
    @SerializedName("steps")
    val steps: List<StepVo>
)

data class StepVo(
    @SerializedName("equipment")
    val equipment: List<EquipmentVo>,
    @SerializedName("ingredients")
    val ingredients: List<IngredientVo>,
    @SerializedName("length")
    val length: LengthVo,
    @SerializedName("number")
    val number: Int,
    @SerializedName("step")
    val step: String
)

data class LengthVo(
    @SerializedName("number")
    val number: Int,
    @SerializedName("unit")
    val unit: String
)

data class EquipmentVo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("temperature")
    val temperature: TemperatureVo
)

data class IngredientVo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String
)

data class TemperatureVo(
    @SerializedName("number")
    val number: Double,
    @SerializedName("unit")
    val unit: String
)
