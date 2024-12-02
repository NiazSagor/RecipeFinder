package com.example.recipefinder.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.repository.recipe.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.filter


sealed class HomeState {
    object Idle : HomeState()
    object Loading : HomeState()
    data class Success(val randomRecipes: List<Recipe>) : HomeState()
    data class Error(val message: String) : HomeState()
}

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private val _homeState =
        MutableStateFlow<HomeState>(HomeState.Idle)

    val homeState = _homeState.asStateFlow()

    init {
        viewModelScope.launch {
            _homeState.value = HomeState.Loading
            try {
                recipeRepository.getRandomRecipes()
                    .collect {
                        if (it != null) _homeState.value = HomeState.Success(it)
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                _homeState.value = HomeState.Error(e.message.toString())
            }
        }
    }

    /*
    * get search result
    * loop one by one by id
    * call get recipe information api
    * save the information datastore
    * observe the datastore to all the recipes and filter
    * */
    suspend fun getSearchResult(query: String, time: Int): List<Recipe> {
        return try {
            val sanitizedQuery = if (query.contains(",")) {
                query.trim().replace(" ", "")
            } else {
                query.trim()
            }
            Log.e(TAG, "getSearchResult: sanitizedQuery $sanitizedQuery", )
            val results = recipeRepository.searchRecipesByIngredients(sanitizedQuery).map {
                Log.e(TAG, "getSearchResult: ${it.title} time $time")
                it.id
            }
            // loop one by one by id
            results.forEach { getRecipeDetailsById(it) }
            val ingredients: List<String> = query.replace(" ", "").split(",")
            Log.e(TAG, "getSearchResult: query ${ingredients.joinToString()}")
            val filteredResult = getMatchedRecipeInformationFromLocal(ingredients, time)
            filteredResult
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<Recipe>()
        }
    }

    suspend fun getRecipeDetailsById(id: Int) {
        try {
            // save the information datastore
            val recipe = recipeRepository.getRecipeById(id)
            if (recipe != null) {
                Log.e(TAG, "getRecipeDetailsById: $id ------- saveRecipeInformation $recipe")
                recipeRepository.saveRecipeInformation(recipe)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getMatchedRecipeInformationFromLocal(
        ingredients: List<String>,
        time: Int
    ): List<Recipe> {
        val allRecipes = recipeRepository.getRandomRecipes().first()
        val result = mutableListOf<Recipe>()
        ingredients.forEach { ingredient ->
            val filtered = allRecipes?.filter {
                (it.title.contains(ingredient) ||
                        it.summary.contains(ingredient) ||
                        it.extendedIngredients.any {
                            if (it.aisle != null) {
                                it.aisle.contains(ingredient)
                            } else false || it.name.contains(ingredient)
                        }) && it.readyInMinutes <= time
            } ?: emptyList()
            if (filtered.isNotEmpty()) result.addAll(filtered)
        }
        Log.e(
            TAG,
            "getMatchedRecipeInformationFromLocal: allRecipes ${allRecipes?.size} result ${result.size} time $time"
        )
        return result
    }
}
