package com.example.recipefinder.ui.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.CommunityPost
import com.example.recipefinder.data.repository.community.CommunityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _communityPosts =
        MutableStateFlow<CommunityScreenState>(CommunityScreenState.Loading)

    val communityPosts = _communityPosts.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _communityPosts.value =
                    CommunityScreenState.Success(communityRepository.getCommunityPosts())
            } catch (e: Exception) {
                _communityPosts.value = CommunityScreenState.Error(e.message ?: "Unknown error")
                e.printStackTrace()
            }
        }
    }
}
