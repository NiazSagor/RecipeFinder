package com.example.recipefinder.ui.post

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.repository.community.post.CommunityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class PostRecipeScreenState {
    object Posting : PostRecipeScreenState()
    object Idle : PostRecipeScreenState()
    object PostSuccess : PostRecipeScreenState()
    data class Error(val message: String) : PostRecipeScreenState()
}

@HiltViewModel
class PostRecipeViewModel @Inject constructor(
    private val communityRepository: CommunityRepository,
) : ViewModel() {

    private val _postState =
        MutableStateFlow<PostRecipeScreenState>(PostRecipeScreenState.Idle)

    val postState = _postState.asStateFlow()

    fun postRecipe(post: String, recipeTitle: String, recipeImageUri: Uri) {
        viewModelScope.launch {
            try {
                _postState.value = PostRecipeScreenState.Posting
                communityRepository.postRecipe(post, recipeTitle, recipeImageUri)
                _postState.value = PostRecipeScreenState.PostSuccess
                delay(2000)
                _postState.value = PostRecipeScreenState.Idle
            } catch (e: Exception) {
                _postState.value = PostRecipeScreenState.Error(e.message.toString())
            }
        }
    }
}
