package com.example.recipefinder.ui.profile

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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProfileState {
    object Idle : ProfileState()
    object Loading : ProfileState()
    data class Success(val data: ProfileData) : ProfileState()
    data class Error(val message: String) : ProfileState()
}

data class ProfileData(
    val bookmarkedRecipes: List<Recipe>,
    val myRatings: List<Recipe>,
    val myTips: List<Recipe>,
)

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val dataStore: RecipeDataStore,
    private val recipeRepository: RecipeRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    val profileState: StateFlow<ProfileState> =
        combine(
            dataStore.getLikedRecipes(),
            dataStore.getTippedRecipes(),
            dataStore.getSavedRecipes(),
        ) { myRatings: List<Recipe>?, myTips: List<Recipe>?, allRecipes: List<Recipe>? ->
            val profileData = ProfileData(
                bookmarkedRecipes = allRecipes?.filter { it.isBookmarked } ?: emptyList(),
                myRatings = myRatings ?: emptyList(),
                myTips = myTips ?: emptyList(),
            )
            ProfileState.Success(profileData) as ProfileState
        }
            .catch { emit(ProfileState.Error(it.message.toString())) }
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

    fun unSaveRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.save(recipe)
        }
    }
}

