package com.example.recipefinder.ui.recipedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.repository.recipe.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RecipeDetailsState {
    object Loading : RecipeDetailsState()
    data class Success(val recipe: Recipe) : RecipeDetailsState()
    data class Error(val message: String) : RecipeDetailsState()
}

sealed class RecipeInstructionsState {
    object Loading : RecipeInstructionsState()
    data class Success(val recipe: Recipe) : RecipeInstructionsState()
    data class Error(val message: String) : RecipeInstructionsState()
}

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private val _recipeDetail =
        MutableStateFlow<RecipeDetailsState>(RecipeDetailsState.Loading)

    val recipeDetail = _recipeDetail.asStateFlow()

    fun getRecipeDetailsById(id: Int) {
        viewModelScope.launch {
            try {
                _recipeDetail.value = RecipeDetailsState.Loading
                val recipeDetail = recipeRepository.getRecipeById(id)
                if (recipeDetail != null) {
                    _recipeDetail.value = RecipeDetailsState.Success(recipeDetail)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _recipeDetail.value = RecipeDetailsState.Error(e.message.toString())
            }
        }
    }

    fun getAnalyzedRecipeInstructions(id: Int) {
        viewModelScope.launch {
            try {

            } catch (e: Exception) {

            }
        }
    }
}
