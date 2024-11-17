package com.example.recipefinder.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.model.SearchRecipeByIngredients
import com.example.recipefinder.data.repository.recipe.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class HomeState {
    object Idle : HomeState()
    object Loading : HomeState()
    data class Success(val randomRecipes: List<Recipe>) : HomeState()
    data class Error(val message: String) : HomeState()
    data class SearchResults(val recipes: List<SearchRecipeByIngredients>): HomeState()
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

    fun getSearchResult(query: String) {
        try {
            viewModelScope.launch {
                val results = recipeRepository.searchRecipesByIngredients(query)
                _homeState.value = HomeState.SearchResults(results)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
