package com.example.recipefinder.ui.recipetipdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.Tip
import com.example.recipefinder.data.repository.tip.RecipeTipsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class RecipeTipsState {
    object Loading : RecipeTipsState()
    data class Success(val tips: List<Tip>) : RecipeTipsState()
    data class Error(val message: String) : RecipeTipsState()
}

@HiltViewModel
class RecipeTipDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    recipeTipsRepository: RecipeTipsRepository
) : ViewModel() {

    val recipeId = savedStateHandle.get<Int>("recipeId")

    val state: StateFlow<RecipeTipsState> =
        recipeTipsRepository.getAllTipsForRecipe(recipeId ?: 0)
            .map { RecipeTipsState.Success(it) }
            .catch { RecipeTipsState.Error(it.message.toString()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = RecipeTipsState.Loading
            )
}
