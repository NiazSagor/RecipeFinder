package com.example.recipefinder.ui.community.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.CommunityPost
import com.example.recipefinder.data.repository.community.post.CommunityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CommunityScreenState {
    object Loading : CommunityScreenState()
    data class Success(val posts: List<CommunityPost>) : CommunityScreenState()
    data class Error(val message: String) : CommunityScreenState()
}

@HiltViewModel
class CommunityScreenViewModel @Inject constructor(
    private val communityRepository: CommunityRepository
) : ViewModel() {

    val state: StateFlow<CommunityScreenState> =
        communityRepository.getCommunityPosts()
            .map { CommunityScreenState.Success(it) as CommunityScreenState }
            .catch { CommunityScreenState.Error(it.message ?: "Unknown error") }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CommunityScreenState.Loading
            )

    fun likePost(postId: String) {
        viewModelScope.launch {
            communityRepository.likePost(postId)
        }
    }

    suspend fun isPostLikedByUser(postId: String): Boolean {
        return try {
            communityRepository.isPostLikedByUser(postId)
        } catch (e: Exception) {
            false
        }
    }
}
