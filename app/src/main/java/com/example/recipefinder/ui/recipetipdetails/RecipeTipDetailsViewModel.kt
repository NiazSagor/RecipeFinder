package com.example.recipefinder.ui.recipetipdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.Tip
import com.example.recipefinder.data.repository.tip.RecipeTipsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RecipeTipsState {
    object Loading : RecipeTipsState()
    data class Success(val tips: List<Tip>) : RecipeTipsState()
    data class Error(val message: String) : RecipeTipsState()
}

@HiltViewModel
class RecipeTipDetailsViewModel @Inject constructor(
    private val recipeTipsRepository: RecipeTipsRepository
) : ViewModel() {

    private val _recipeTips = MutableStateFlow<RecipeTipsState>(RecipeTipsState.Loading)

    val recipeTips = _recipeTips.asStateFlow()

    fun getRecipeTips(recipeId: Int) {
        viewModelScope.launch {
            try {
                _recipeTips.value = RecipeTipsState.Loading
                val recipeTips = recipeTipsRepository.getAllTipsForRecipe(recipeId)
                _recipeTips.value = RecipeTipsState.Success(recipeTips)
            } catch (e: Exception) {
                e.printStackTrace()
                _recipeTips.value = RecipeTipsState.Error(e.message.toString())
            }
        }
    }
}
