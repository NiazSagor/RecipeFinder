package com.example.recipefinder.data.dummy

import com.example.recipefinder.data.model.Ingredient
import com.example.recipefinder.data.model.Measure
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.model.Metric
import com.example.recipefinder.model.Us

val recipeList = listOf(
    Recipe(
        id = 1,
        title = "Creamy Butter Chicken",
        image = "https://img.spoonacular.com/recipes/1-312x231.jpg",
        summary = "A delicious Creamy Butter Chicken recipe.",
        servings = 4,
        readyInMinutes = 45,
        cookingMinutes = 30,
        preparationMinutes = 15,
        like = 120,
        dishTypes = listOf("Main Course", "Dinner"),
        extendedIngredients = listOf(
            Ingredient(
                aisle = "Meat",
                amount = 500.0,
                consitency = "solid",
                id = 101,
                image = "https://img.spoonacular.com/ingredients_100x100/chicken.png",
                measures = Measure(
                    metric = Metric(500.0, "grams", "g"),
                    us = Us(17.64, "ounces", "oz")
                ),
                meta = listOf("boneless", "skinless"),
                name = "chicken",
                unit = "grams"
            )
        ),
        isBookmarked = false
    ),
    Recipe(
        id = 2,
        title = "Apple Cinnamon Tart",
        image = "https://img.spoonacular.com/recipes/2-312x231.jpg",
        summary = "A delicious Apple Cinnamon Tart recipe.",
        servings = 6,
        readyInMinutes = 40,
        cookingMinutes = 25,
        preparationMinutes = 15,
        like = 85,
        dishTypes = listOf("Dessert", "Snack"),
        extendedIngredients = listOf(
            Ingredient(
                aisle = "Produce",
                amount = 3.0,
                consitency = "solid",
                id = 9003,
                image = "https://img.spoonacular.com/ingredients_100x100/red-delicious-apples.png",
                measures = Measure(
                    metric = Metric(3.0, "large", "large"),
                    us = Us(3.0, "large", "large")
                ),
                meta = listOf("red"),
                name = "red apples",
                unit = "large"
            )
        ),
        isBookmarked = false
    ),
    Recipe(
        id = 3,
        title = "Fluffy Pancakes",
        image = "https://img.spoonacular.com/recipes/3-312x231.jpg",
        summary = "A delicious Fluffy Pancakes recipe.",
        servings = 3,
        readyInMinutes = 35,
        cookingMinutes = 20,
        preparationMinutes = 15,
        like = 200,
        dishTypes = listOf("Breakfast", "Dessert"),
        extendedIngredients = listOf(
            Ingredient(
                aisle = "Produce",
                amount = 1.0,
                consitency = "solid",
                id = 9050,
                image = "https://img.spoonacular.com/ingredients_100x100/banana.jpg",
                measures = Measure(
                    metric = Metric(1.0, "medium", "medium"),
                    us = Us(1.0, "medium", "medium")
                ),
                meta = listOf("ripe"),
                name = "banana",
                unit = "medium"
            )
        ),
        isBookmarked = false
    ),
    Recipe(
        id = 4,
        title = "Grilled Salmon with Herbs",
        image = "https://img.spoonacular.com/recipes/4-312x231.jpg",
        summary = "A delicious Grilled Salmon with Herbs recipe.",
        servings = 2,
        readyInMinutes = 30,
        cookingMinutes = 20,
        preparationMinutes = 10,
        like = 65,
        dishTypes = listOf("Main Course", "Dinner"),
        extendedIngredients = listOf(
            Ingredient(
                aisle = "Produce",
                amount = 1.0,
                consitency = "solid",
                id = 2044,
                image = "https://img.spoonacular.com/ingredients_100x100/fresh-basil.jpg",
                measures = Measure(
                    metric = Metric(1.0, "bunch", "bunch"),
                    us = Us(1.0, "bunch", "bunch")
                ),
                meta = listOf("fresh"),
                name = "basil",
                unit = "bunch"
            ),
            Ingredient(
                aisle = "Spices and Seasonings",
                amount = 1.0,
                consitency = "solid",
                id = 2027,
                image = "https://img.spoonacular.com/ingredients_100x100/rosemary.jpg",
                measures = Measure(
                    metric = Metric(1.0, "sprig", "sprig"),
                    us = Us(1.0, "sprig", "sprig")
                ),
                meta = listOf("fresh"),
                name = "rosemary",
                unit = "sprig"
            )
        ),
        isBookmarked = false
    )
)
