package com.example.recipefinder.ui.home

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
            val initialSearchResult = getMatchedRecipeInformationFromLocal(query, time)
            if (initialSearchResult.isEmpty()) {
                // get search result
                val results = recipeRepository.searchRecipesByIngredients(query).map { it.id }
                // loop one by one by id
                results.forEach { getRecipeDetailsById(it) }
                val filteredResult = getMatchedRecipeInformationFromLocal(query, time)
                filteredResult
            } else {
                initialSearchResult
            }
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
                recipeRepository.saveRecipeInformation(recipe)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getMatchedRecipeInformationFromLocal(query: String, time: Int): List<Recipe> {
        val allRecipes = recipeRepository.getRandomRecipes().first()
        return allRecipes?.filter {
            it.extendedIngredients.any {
                it.name.contains(
                    query,
                    ignoreCase = true
                )
            } && it.readyInMinutes <= time
        } ?: emptyList()
    }
}
