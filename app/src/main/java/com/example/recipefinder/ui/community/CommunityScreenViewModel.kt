package com.example.recipefinder.ui.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.CommunityPost
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

) : ViewModel() {

    private val _communityPosts =
        MutableStateFlow<CommunityScreenState>(CommunityScreenState.Loading)

    val communityPosts = _communityPosts.asStateFlow()

    init {
        viewModelScope.launch {
            try {

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
