package com.example.recipefinder.model

import com.example.recipefinder.data.model.Ingredient
import com.example.recipefinder.data.model.Measure
import com.example.recipefinder.data.model.Recipe

data class RandomRecipesVo(
    val recipes: List<RecipeInformationVo>
)

data class SearchRecipesVo(
    val results: List<RecipeInformationVo>,
    val offset: Int,
    val number: Int,
    val totalResults: Int
)

fun List<RecipeInformationVo>.toInternalRecipesModel(): List<Recipe> {
    return this.map {
        it.toInternalRecipeModel()
    }
}

fun RecipeInformationVo.toInternalRecipeModel() =
    Recipe(
        id = this.id,
        title = this.title,
        image = this.image,
        summary = this.summary,
        servings = this.servings,
        readyInMinutes = this.readyInMinutes,
        cookingMinutes = this.cookingMinutes ?: 0,
        preparationMinutes = this.preparationMinutes ?: 0,
        like = this.aggregateLikes,
        dishTypes = this.dishTypes,
        extendedIngredients = this.extendedIngredients?.toInternalIngredientsModel() ?: emptyList(),
    )

fun List<ExtendedIngredientVo>.toInternalIngredientsModel(): List<Ingredient> {
    return this.map {
        it.toInternalIngredientModel()
    }
}

fun ExtendedIngredientVo.toInternalIngredientModel(): Ingredient =
    Ingredient(
        aisle = this.aisle,
        amount = this.amount,
        consitency = this.consitency,
        id = this.id,
        image = this.image,
        measures = this.measures.toIngredientModel(),
        meta = this.meta,
        name = this.name,
        unit = this.unit
    )


fun MeasuresVo.toIngredientModel(): Measure =
    Measure(
        metric = this.metric,
        us = this.us
    )
