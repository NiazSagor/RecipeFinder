package com.example.recipefinder.ui.recipedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.model.RecipeAnalyzedInstructions
import com.example.recipefinder.data.model.RecipeNutrient
import com.example.recipefinder.data.repository.recipe.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private val _recipeDetail =
        MutableStateFlow<RecipeDetailsState>(RecipeDetailsState.Loading)

    private val _recipeInstructions =
        MutableStateFlow<RecipeInstructionsState>(RecipeInstructionsState.Loading)

    val recipeDetail = _recipeDetail.asStateFlow()

    val recipeInstructions = _recipeInstructions.asStateFlow()

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
                _recipeInstructions.value = RecipeInstructionsState.Loading
                val recipeInstructions = recipeRepository.getAnalyzedInstructions(id)
                _recipeInstructions.value = RecipeInstructionsState.Success(recipeInstructions)
            } catch (e: Exception) {
                e.printStackTrace()
                _recipeInstructions.value = RecipeInstructionsState.Error(e.message.toString())
            }
        }
    }

    suspend fun getSimilarRecipes(id: Int): List<Recipe> {
        // TODO:
//        val similarRecipes = recipeRepository.getSimilarRecipes(id).map { it.id }
//        similarRecipes.forEach { getRecipeInformationById(it) }
//        val similarRecipeDetails = recipeRepository.getRandomRecipes().first()?.filter { it.id in similarRecipes }
        val similarRecipeDetails = recipeRepository.getRandomRecipes().first()?.take(10) // just take from local
        return similarRecipeDetails ?: emptyList()
    }

    suspend fun getRecipeInformationById(id: Int) {
        try {
            val recipe = recipeRepository.getRecipeById(id)
            if (recipe != null) {
                recipeRepository.saveRecipeInformation(recipe)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getNutrients(id: Int) : RecipeNutrient {
        return recipeRepository.getNutrients(id)
    }
}
