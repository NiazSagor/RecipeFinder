package com.example.recipefinder.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.repository.recipe.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


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
}