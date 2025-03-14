package com.example.recipefinder.ui.recipedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.model.RecipeAnalyzedInstructions
import com.example.recipefinder.data.model.RecipeNutrient
import com.example.recipefinder.data.repository.recipe.RecipeRepository
import com.example.recipefinder.datastore.RecipeDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    data class Success(val recipeInstructions: RecipeAnalyzedInstructions) :
        RecipeInstructionsState()
    data class Error(val message: String) : RecipeInstructionsState()
}

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeDataStore: RecipeDataStore,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private val _recipeDetailState =
        MutableStateFlow<RecipeDetailsState>(RecipeDetailsState.Loading)

    private val _recipeInstructionsState =
        MutableStateFlow<RecipeInstructionsState>(RecipeInstructionsState.Loading)

    val recipeDetailState: StateFlow<RecipeDetailsState> = _recipeDetailState.asStateFlow()

    val recipeInstructionsState: StateFlow<RecipeInstructionsState> =
        _recipeInstructionsState.asStateFlow()

    fun getRecipeDetailsById(recipeId: Int) {
        viewModelScope.launch {
            try {
                _recipeDetailState.value = RecipeDetailsState.Loading
                val recipeDetail: Recipe? = recipeRepository.getRecipeDetail(recipeId)
                if (recipeDetail != null) {
                    _recipeDetailState.value = RecipeDetailsState.Success(recipeDetail)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _recipeDetailState.value = RecipeDetailsState.Error(e.message.toString())
            }
        }
    }

    fun getAnalyzedRecipeInstructions(recipeId: Int) {
        viewModelScope.launch {
            try {
                _recipeInstructionsState.value = RecipeInstructionsState.Loading
                val recipeInstructions: RecipeAnalyzedInstructions =
                    recipeRepository.getAnalyzedInstructions(recipeId)
                _recipeInstructionsState.value = RecipeInstructionsState.Success(recipeInstructions)
            } catch (e: Exception) {
                e.printStackTrace()
                _recipeInstructionsState.value = RecipeInstructionsState.Error(e.message.toString())
            }
        }
    }

    suspend fun getNutrients(recipeId: Int): RecipeNutrient {
        return recipeRepository.getNutrients(recipeId)
    }

    fun sendTip(recipeId: Int, tip: String, recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.sendTip(recipeId, tip)
            recipeDataStore.tippedRecipe(recipe)
        }
    }

    fun like(recipeId: Int, recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.like(recipeId)
            recipeDataStore.likeRecipe(recipe)
        }
    }

    fun save(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.save(recipe)
        }
    }
}
