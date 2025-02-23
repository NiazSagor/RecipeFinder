package com.example.recipefinder.ui.profile.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.repository.recipe.RecipeRepository
import com.example.recipefinder.data.repository.user.UserRepository
import com.example.recipefinder.datastore.RecipeDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProfileState {
    object Idle : ProfileState()
    object Loading : ProfileState()
    data class Success(val bookmarkedRecipes: List<Recipe>) : ProfileState()
    data class Error(val message: String) : ProfileState()
}

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val dataStore: RecipeDataStore,
    private val recipeRepository: RecipeRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Idle)
    val profileState = _profileState.asStateFlow()

    init {
        viewModelScope.launch {
            _profileState.value = ProfileState.Loading
            try {
                _profileState.value = ProfileState.Success(dataStore.getBookmarkedRecipes())
            } catch (e: Exception) {
                e.printStackTrace()
                _profileState.value = ProfileState.Error(e.message.toString())
            }
        }
    }

    suspend fun getRecipeLike(id: Int): Int {
        return try {
            recipeRepository.getLikesForRecipes(id)
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    fun getUserName(): String = userRepository.getName()

    fun getUserProfilePhoto(): String = userRepository.getPhoto().toString()

    fun saveRecipe(recipe: Recipe) {
        viewModelScope.launch {
            dataStore.bookmarkRecipe(recipe)
        }
    }
}

