package com.example.recipefinder.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.repository.recipe.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.collections.filter


sealed class HomeState {
    object Idle : HomeState()
    object Loading : HomeState()
    data class Success(val recipes: List<Recipe>) : HomeState()
    data class Error(val message: String) : HomeState()
}

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    val homeState: StateFlow<HomeState> =
        recipeRepository.getRandomRecipes()
            .map { recipes: List<Recipe> ->
                HomeState.Success(recipes)
            }
            .catch { HomeState.Error(it.message.toString()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeState.Idle
            )

    suspend fun search(
        searchType: String,
        query: String,
        time: Int,
        mealType: String
    ): List<Recipe> {
        return if (searchType == "Ingredient") {
            getSearchResult(query = query, time = time)
        } else {
            getComplexSearchResult(query = query, time = time, dishType = mealType)
        }
    }


    /**
     * Retrieves a list of recipes that match a given search query and time constraint.
     *
     * This function performs the following steps:
     * 1. **Sanitizes the search query:**
     *    - If the query contains commas, it removes all spaces and trims the query.
     *    - Otherwise, it only trims the query.
     * 2. **Searches for recipes by ingredients:**
     *    - Uses the `recipeRepository` to search for recipes based on the sanitized query.
     *    - Retrieves the IDs of the matching recipes.
     * 3. **Fetches detailed recipe information:**
     *    - Iterates through the recipe IDs and calls `getRecipeDetailsById` for each ID to fetch more details (presumably from an external source).
     *    - Note: the fetched details are not explicitly returned or used in the function, they might be stored in a database or cached elsewhere.
     * 4. **Filters the results based on local data and time constraint:**
     *    - Splits the original query string (with spaces removed) into a list of individual ingredients.
     *    - Uses the `getMatchedRecipeInformationFromLocal` function to filter the recipes, likely comparing with local data to find recipes that match the ingredients and are within the specified time.
     * 5. **Handles exceptions:**
     *    - If any exception occurs during the process, it prints the stack trace and returns an empty list.
     *
     * @param query The search query string, which can contain multiple ingredients separated by commas.
     * @param time The time constraint used for filtering recipes. The unit of time is not specified in this function, it's managed by the `getMatchedRecipeInformationFromLocal`.
     * @return A list of `Recipe` objects that match the search query and time constraint, or an empty list if no matches are found or an error occurs.
     * @throws Exception if any problem occurs during the process of fetching data or filtering.
     */
    private suspend fun getSearchResult(query: String, time: Int): List<Recipe> {
        return try {
            val sanitizedQuery =
                if (query.contains(",")) {
                    query.trim().replace(" ", "")
                } else {
                    query.trim()
                }
            val searchRecipeIds: List<Int> =
                recipeRepository
                    .searchRecipesByIngredients(ingredients = sanitizedQuery)
                    .map { searchResult ->
                        searchResult.id
                    }
            // loop one by one by id
            searchRecipeIds.forEach { recipeId ->
                getRecipeDetailsById(recipeId)
            }
            val ingredients: List<String> = sanitizedQuery.split(",")
            val filteredResult: List<Recipe> = getMatchedRecipeInformationFromLocal(
                ingredients = ingredients,
                time = time
            )
            filteredResult
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<Recipe>()
        }
    }


    /**
     * Retrieves a list of recipes based on a complex search criteria.
     *
     * This function performs a search for recipes using a combination of a query string,
     * a maximum preparation time, and a dish type. It also sanitizes the query string
     * before performing the search.
     *
     * @param query The main search query string. If it contains commas, it's treated as a list of ingredients
     *              and spaces are removed. Otherwise, it's treated as a general search term.
     * @param time The maximum preparation time (in minutes) for the recipes.
     * @param dishType The type of dish to search for (e.g., "main course", "dessert", "appetizer").
     * @return A list of `Recipe` objects that match the search criteria.
     *         Returns an empty list if no matching recipes are found or if an error occurs.
     *
     * @throws Exception if an error occurs during the search process, it will be caught and printed to
     *                   the console, and an empty list will be returned.
     */
    private suspend fun getComplexSearchResult(
        query: String,
        time: Int,
        dishType: String
    ): List<Recipe> {
        return try {
            val sanitizedQuery = if (query.contains(",")) {
                query.trim().replace(" ", "")
            } else {
                query.trim()
            }
            val result: List<Recipe> = recipeRepository.searchMealType(
                query = sanitizedQuery,
                type = dishType,
                maxReadyTime = time,
            )
            result
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<Recipe>()
        }
    }

    private suspend fun getRecipeDetailsById(recipeId: Int) {
        try {
            // save the information datastore
            val recipe: Recipe? = recipeRepository.getRecipeDetail(recipeId)
            if (recipe != null) {
                recipeRepository.addNewRecipe(recipe)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun getMatchedRecipeInformationFromLocal(
        ingredients: List<String>,
        time: Int
    ): List<Recipe> {
        return withContext(Dispatchers.Default) {
            val allRecipes: List<Recipe> = recipeRepository.getRandomRecipes().first()
            val result: MutableList<Recipe> = mutableListOf<Recipe>()
            ingredients.forEach { ingredient: String ->
                val filtered: List<Recipe> = allRecipes.filter {
                    (it.title.contains(ingredient) ||
                            it.summary.contains(ingredient) ||
                            it.extendedIngredients.any {
                                if (it.aisle != null) {
                                    it.aisle.contains(ingredient)
                                } else false || it.name.contains(ingredient)
                            }) && it.readyInMinutes <= time
                }
                if (filtered.isNotEmpty()) result.addAll(filtered)
            }
            result
        }
    }

    // returns like count of a recipe
    suspend fun getRecipeLikeCount(recipeId: Int): Int {
        return try {
            recipeRepository.getLikesForRecipes(recipeId)
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    fun save(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.save(recipe)
        }
    }
}
