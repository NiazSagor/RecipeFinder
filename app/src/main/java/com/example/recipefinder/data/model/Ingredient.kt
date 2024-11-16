package com.example.recipefinder.data.model

import com.example.recipefinder.model.Metric
import com.example.recipefinder.model.Us

data class Ingredient(
    val aisle: String,
    val amount: Double,
    val consitency: String,
    val id: Int,
    val image: String,
    val measures: Measure,
    val meta: List<String>,
    val name: String,
    val unit: String
)

data class Measure(
    val metric: Metric,
    val us: Us
)
