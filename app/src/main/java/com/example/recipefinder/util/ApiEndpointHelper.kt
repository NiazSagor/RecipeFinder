package com.example.recipefinder.util

import com.example.recipefinder.ui.home.components.Bath_outdoor
import com.example.recipefinder.ui.home.components.Breakfast_dining
import com.example.recipefinder.ui.home.components.Dessert
import com.example.recipefinder.ui.home.components.Fastfood
import com.example.recipefinder.ui.home.components.FishSymbol
import com.example.recipefinder.ui.home.components.Food_bank
import com.example.recipefinder.ui.home.components.Local_drink
import com.example.recipefinder.ui.home.components.Rice_bowl
import com.example.recipefinder.ui.home.components.Salad
import com.example.recipefinder.ui.home.components.Sandwich
import com.example.recipefinder.ui.home.components.Set_meal
import com.example.recipefinder.ui.home.components.Soup_kitchen
import com.example.recipefinder.ui.home.components.Water_bottle
import com.example.recipefinder.ui.model.MealType


val mealType = listOf(
    MealType("main course", Food_bank),
    MealType("side dish", Rice_bowl),
    MealType("dessert", Dessert),
    MealType("appetizer", Sandwich),
    MealType("salad", Salad),
    MealType("bread", Breakfast_dining),
    MealType("breakfast", Breakfast_dining),
    MealType("sauce", Bath_outdoor),
    MealType("soup", Soup_kitchen),
    MealType("beverage", Water_bottle),
    MealType("marinade", FishSymbol),
    MealType("fingerfood", Set_meal),
    MealType("snack", Fastfood),
    MealType("drink", Local_drink),
)

val readyTime = listOf(5, 20, 45, 60)
