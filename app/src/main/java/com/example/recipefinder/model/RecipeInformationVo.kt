package com.example.recipefinder.model


data class RecipeInformationVo(
    val aggregateLikes: Int,
    val analyzedInstructions: List<Any>?,
    val cheap: Boolean,
    val creditsText: String,
    val cuisines: List<Any>?,
    val dairyFree: Boolean,
    val diets: List<Any>,
    val dishTypes: List<String>,
    val extendedIngredients: List<ExtendedIngredientVo>? = emptyList(),
    val gaps: String,
    val glutenFree: Boolean,
    val healthScore: Int,
    val id: Int,
    val image: String,
    val imageType: String,
    val instructions: String,
    val ketogenic: Boolean,
    val license: String?,
    val lowFodmap: Boolean,
    val occasions: List<Any>?,
    val pricePerServing: Double,
    val readyInMinutes: Int,
    val cookingMinutes: Int?,
    val preparationMinutes: Int?,
    val servings: Int,
    val sourceName: String,
    val sourceUrl: String,
    val spoonacularScore: Double,
    val spoonacularSourceUrl: String,
    val summary: String,
    val sustainable: Boolean,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val weightWatcherSmartPoints: Int,
    val whole30: Boolean,
    val winePairing: WinePairingVo
)

data class ExtendedIngredientVo(
    val aisle: String,
    val amount: Double,
    val consitency: String,
    val id: Int,
    val image: String,
    val measures: MeasuresVo,
    val meta: List<String>,
    val name: String,
    val original: String,
    val originalName: String,
    val unit: String
)

data class WinePairingVo(
    val pairedWines: List<String>,
    val pairingText: String,
    val productMatches: List<ProductMatchesVo>
)

data class MeasuresVo(
    val metric: Metric,
    val us: Us
)

data class Metric(
    val amount: Double,
    val unitLong: String,
    val unitShort: String
)

data class Us(
    val amount: Double,
    val unitLong: String,
    val unitShort: String
)

data class ProductMatchesVo(
    val averageRating: Double,
    val description: String,
    val id: Int,
    val imageUrl: String,
    val link: String,
    val price: String,
    val ratingCount: Int,
    val score: Double,
    val title: String
)