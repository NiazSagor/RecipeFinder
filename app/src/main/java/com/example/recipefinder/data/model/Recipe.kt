package com.example.recipefinder.data.model

data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String,
    val servings: Int,
    val readyInMinutes: Int,
    val cookingMinutes: Int,
    val preparationMinutes: Int,
    val like: Int,
    val dishTypes: List<String>,
    val extendedIngredients: List<Ingredient>,
    var isBookmarked: Boolean = false
)
