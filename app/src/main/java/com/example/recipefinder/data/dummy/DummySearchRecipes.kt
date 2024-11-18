package com.example.recipefinder.data.dummy

import com.example.recipefinder.data.model.MissedIngredient
import com.example.recipefinder.data.model.SearchRecipeByIngredients
import com.example.recipefinder.data.model.UnusedIngredient
import com.example.recipefinder.data.model.UsedIngredient


val results = listOf(
    SearchRecipeByIngredients(
        id = 1,
        image = "https://img.spoonacular.com/recipes/1-312x231.jpg",
        imageType = "jpg",
        likes = 120,
        missedIngredientCount = 2,
        missedIngredients = listOf(
            MissedIngredient(
                aisle = "Milk, Eggs, Other Dairy",
                amount = 1.5,
                id = 1001,
                image = "https://img.spoonacular.com/ingredients_100x100/butter-sliced.jpg",
                meta = listOf("unsalted", "cold"),
                name = "butter",
                original = "1 1/2 sticks cold unsalted butter",
                originalName = "cold unsalted butter",
                unit = "sticks",
                unitLong = "sticks",
                unitShort = "sticks"
            )
        ),
        title = "Creamy Butter Chicken",
        unusedIngredients = listOf(
            UnusedIngredient(
                aisle = "Produce",
                amount = 1.0,
                id = 9003,
                image = "https://img.spoonacular.com/ingredients_100x100/apple.jpg",
                meta = emptyList(),
                name = "apple",
                original = "apple",
                originalName = "apple",
                unit = "serving",
                unitLong = "serving",
                unitShort = "serving"
            )
        ),
        usedIngredientCount = 3,
        usedIngredients = listOf(
            UsedIngredient(
                aisle = "Meat",
                amount = 500.0,
                id = 101,
                image = "https://img.spoonacular.com/ingredients_100x100/chicken.png",
                meta = listOf("boneless", "skinless"),
                name = "chicken",
                original = "500g boneless skinless chicken",
                originalName = "boneless skinless chicken",
                unit = "grams",
                unitLong = "grams",
                unitShort = "g"
            )
        )
    ),
    SearchRecipeByIngredients(
        id = 2,
        image = "https://img.spoonacular.com/recipes/2-312x231.jpg",
        imageType = "jpg",
        likes = 85,
        missedIngredientCount = 1,
        missedIngredients = listOf(
            MissedIngredient(
                aisle = "Spices and Seasonings",
                amount = 2.0,
                id = 2010,
                image = "https://img.spoonacular.com/ingredients_100x100/cinnamon.jpg",
                meta = emptyList(),
                name = "cinnamon",
                original = "2 teaspoons cinnamon",
                originalName = "cinnamon",
                unit = "teaspoons",
                unitLong = "teaspoons",
                unitShort = "tsp"
            )
        ),
        title = "Apple Cinnamon Tart",
        unusedIngredients = emptyList(),
        usedIngredientCount = 2,
        usedIngredients = listOf(
            UsedIngredient(
                aisle = "Produce",
                amount = 3.0,
                id = 9003,
                image = "https://img.spoonacular.com/ingredients_100x100/red-delicious-apples.png",
                meta = listOf("red"),
                name = "red apples",
                original = "3 large red apples",
                originalName = "red apples",
                unit = "large",
                unitLong = "larges",
                unitShort = "large"
            )
        )
    ),

    SearchRecipeByIngredients(
        id = 3,
        image = "https://img.spoonacular.com/recipes/3-312x231.jpg",
        imageType = "jpg",
        likes = 200,
        missedIngredientCount = 3,
        missedIngredients = listOf(
            MissedIngredient(
                aisle = "Baking",
                amount = 2.0,
                id = 18372,
                image = "https://img.spoonacular.com/ingredients_100x100/baking-powder.jpg",
                meta = listOf("sifted"),
                name = "baking powder",
                original = "2 teaspoons sifted baking powder",
                originalName = "sifted baking powder",
                unit = "teaspoons",
                unitLong = "teaspoons",
                unitShort = "tsp"
            ),
            MissedIngredient(
                aisle = "Milk, Eggs, Other Dairy",
                amount = 1.0,
                id = 1123,
                image = "https://img.spoonacular.com/ingredients_100x100/egg.jpg",
                meta = emptyList(),
                name = "egg",
                original = "1 large egg",
                originalName = "egg",
                unit = "large",
                unitLong = "large",
                unitShort = "large"
            )
        ),
        title = "Fluffy Pancakes",
        unusedIngredients = emptyList(),
        usedIngredientCount = 2,
        usedIngredients = listOf(
            UsedIngredient(
                aisle = "Produce",
                amount = 1.0,
                id = 9050,
                image = "https://img.spoonacular.com/ingredients_100x100/banana.jpg",
                meta = listOf("ripe"),
                name = "banana",
                original = "1 ripe banana",
                originalName = "banana",
                unit = "medium",
                unitLong = "medium",
                unitShort = "medium"
            )
        )
    ),
    SearchRecipeByIngredients(
        id = 4,
        image = "https://img.spoonacular.com/recipes/4-312x231.jpg",
        imageType = "jpg",
        likes = 65,
        missedIngredientCount = 1,
        missedIngredients = listOf(
            MissedIngredient(
                aisle = "Seafood",
                amount = 2.0,
                id = 15076,
                image = "https://img.spoonacular.com/ingredients_100x100/salmon.jpg",
                meta = listOf("fresh"),
                name = "salmon fillets",
                original = "2 fresh salmon fillets",
                originalName = "salmon fillets",
                unit = "fillets",
                unitLong = "fillets",
                unitShort = "fillets"
            )
        ),
        title = "Grilled Salmon with Herbs",
        unusedIngredients = emptyList(),
        usedIngredientCount = 3,
        usedIngredients = listOf(
            UsedIngredient(
                aisle = "Produce",
                amount = 1.0,
                id = 2044,
                image = "https://img.spoonacular.com/ingredients_100x100/fresh-basil.jpg",
                meta = listOf("fresh"),
                name = "basil",
                original = "1 bunch fresh basil",
                originalName = "fresh basil",
                unit = "bunch",
                unitLong = "bunch",
                unitShort = "bunch"
            ),
            UsedIngredient(
                aisle = "Spices and Seasonings",
                amount = 1.0,
                id = 2027,
                image = "https://img.spoonacular.com/ingredients_100x100/rosemary.jpg",
                meta = listOf("fresh"),
                name = "rosemary",
                original = "1 sprig fresh rosemary",
                originalName = "fresh rosemary",
                unit = "sprig",
                unitLong = "sprig",
                unitShort = "sprig"
            )
        )
    )
)
