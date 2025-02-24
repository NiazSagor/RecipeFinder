package com.example.recipefinder.ui.profile.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.repository.recipe.RecipeRepository
import com.example.recipefinder.data.repository.user.UserRepository
import com.example.recipefinder.datastore.RecipeDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    val profileState: StateFlow<ProfileState> =
        dataStore.getRandomRecipes()
            .map { allRecipes ->
                val bookMarked = allRecipes?.filter { it.isBookmarked }
                ProfileState.Success(bookMarked ?: emptyList()) as ProfileState
            }.catch { emit(ProfileState.Error(it.message ?: "Unknown error")) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ProfileState.Loading
            )

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

