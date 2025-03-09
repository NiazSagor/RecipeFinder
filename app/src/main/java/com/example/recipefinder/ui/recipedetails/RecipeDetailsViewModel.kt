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
    data class Success(val recipe: RecipeAnalyzedInstructions) : RecipeInstructionsState()
    data class Error(val message: String) : RecipeInstructionsState()
}

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeDataStore: RecipeDataStore,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private val _recipeDetail =
        MutableStateFlow<RecipeDetailsState>(RecipeDetailsState.Loading)

    private val _recipeInstructions =
        MutableStateFlow<RecipeInstructionsState>(RecipeInstructionsState.Loading)

    val recipeDetail: StateFlow<RecipeDetailsState> = _recipeDetail.asStateFlow()

    val recipeInstructions: StateFlow<RecipeInstructionsState> = _recipeInstructions.asStateFlow()

    fun getRecipeDetailsById(id: Int) {
        viewModelScope.launch {
            try {
                _recipeDetail.value = RecipeDetailsState.Loading
                val recipeDetail = recipeRepository.getRecipeDetail(id)
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
                _recipeInstructions.value = RecipeInstructionsState.Loading
                val recipeInstructions = recipeRepository.getAnalyzedInstructions(id)
                _recipeInstructions.value = RecipeInstructionsState.Success(recipeInstructions)
            } catch (e: Exception) {
                e.printStackTrace()
                _recipeInstructions.value = RecipeInstructionsState.Error(e.message.toString())
            }
        }
    }

    suspend fun getNutrients(id: Int): RecipeNutrient {
        return recipeRepository.getNutrients(id)
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
