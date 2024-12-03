package com.example.recipefinder.model

data class SimilarRecipeItemVo(
    val id: Int,
    val imageType: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val title: String
)
