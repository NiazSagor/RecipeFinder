package com.example.recipefinder.model

import com.example.recipefinder.data.model.MissedIngredient
import com.example.recipefinder.data.model.SearchRecipeByIngredients
import com.example.recipefinder.data.model.UnusedIngredient
import com.example.recipefinder.data.model.UsedIngredient

fun List<SearchRecipeByIngredientsResponseVo>.toInternalSearchRecipesByIngredients(): List<SearchRecipeByIngredients> {
    return this.map {
        it.toInternalSearchRecipeByIngredients()
    }
}

fun SearchRecipeByIngredientsResponseVo.toInternalSearchRecipeByIngredients():
        SearchRecipeByIngredients {
    return SearchRecipeByIngredients(
        id = this.id,
        image = this.image,
        imageType = this.imageType,
        likes = this.likes,
        missedIngredientCount = this.missedIngredientCount,
        missedIngredients = this.missedIngredients.toInternalMissedIngredients(),
        title = this.title,
        unusedIngredients = this.unusedIngredients.toInternalUnusedIngredients(),
        usedIngredientCount = this.usedIngredientCount,
        usedIngredients = this.usedIngredients.toInternalUsedIngredients()
    )
}

fun List<MissedIngredientResponseVo>.toInternalMissedIngredients():
        List<MissedIngredient> {
    return this.map {
        it.toInternalMissedIngredient()
    }
}

fun MissedIngredientResponseVo.toInternalMissedIngredient():
        MissedIngredient {
    return MissedIngredient(
        aisle = this.aisle,
        amount = this.amount,
        id = this.id,
        image = this.image,
        meta = this.meta,
        name = this.name,
        original = this.original,
        originalName = this.originalName,
        unit = this.unit,
        unitLong = this.unitLong,
        unitShort = this.unitShort
    )
}

fun List<UnusedIngredientResponseVo>.toInternalUnusedIngredients():
        List<UnusedIngredient> {
    return this.map {
        it.toInternalUnusedIngredient()
    }
}

fun UnusedIngredientResponseVo.toInternalUnusedIngredient():
        UnusedIngredient {
    return UnusedIngredient(
        aisle = this.aisle,
        amount = this.amount,
        id = this.id,
        image = this.image,
        meta = this.meta,
        name = this.name,
        original = this.original,
        originalName = this.originalName,
        unit = this.unit,
        unitLong = this.unitLong,
        unitShort = this.unitShort
    )
}

fun List<UsedIngredientResponseVo>.toInternalUsedIngredients():
        List<UsedIngredient> {
    return this.map {
        it.toInternalUsedIngredient()
    }
}

fun UsedIngredientResponseVo.toInternalUsedIngredient():
        UsedIngredient {
    return UsedIngredient(
        aisle = this.aisle,
        amount = this.amount,
        id = this.id,
        image = this.image,
        meta = this.meta,
        name = this.name,
        original = this.original,
        originalName = this.originalName,
        unit = this.unit,
        unitLong = this.unitLong,
        unitShort = this.unitShort
    )
}

data class SearchRecipeByIngredientsResponseVo(
    val id: Int,
    val image: String,
    val imageType: String,
    val likes: Int,
    val missedIngredientCount: Int,
    val missedIngredients: List<MissedIngredientResponseVo>,
    val title: String,
    val unusedIngredients: List<UnusedIngredientResponseVo>,
    val usedIngredientCount: Int,
    val usedIngredients: List<UsedIngredientResponseVo>
)

data class MissedIngredientResponseVo(
    val aisle: String,
    val amount: Double,
    val id: Int,
    val image: String,
    val meta: List<String>,
    val name: String,
    val original: String,
    val originalName: String,
    val unit: String,
    val unitLong: String,
    val unitShort: String
)

data class UnusedIngredientResponseVo(
    val aisle: String,
    val amount: Double,
    val id: Int,
    val image: String,
    val meta: List<String>,
    val name: String,
    val original: String,
    val originalName: String,
    val unit: String,
    val unitLong: String,
    val unitShort: String
)

data class UsedIngredientResponseVo(
    val aisle: String,
    val amount: Double,
    val id: Int,
    val image: String,
    val meta: List<String>,
    val name: String,
    val original: String,
    val originalName: String,
    val unit: String,
    val unitLong: String,
    val unitShort: String
)
