package com.example.recipefinder.ui.community.postcomments

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.CommunityPost
import com.example.recipefinder.data.model.PostComment
import com.example.recipefinder.data.repository.community.CommunityRepository
import com.example.recipefinder.data.repository.community.PostCommentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PostCommentData(
    val communityPost: CommunityPost,
    val comments: List<PostComment>
)

sealed class PostCommentState {
    object Loading : PostCommentState()
    data class Success(val data: PostCommentData) : PostCommentState()
    data class Error(val message: String) : PostCommentState()
}

@HiltViewModel
class PostCommentsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postCommentsRepository: PostCommentsRepository,
    private val communityRepository: CommunityRepository,
) : ViewModel() {

    val postId = savedStateHandle.getStateFlow<String>("postId", "")

    val state: StateFlow<PostCommentState> =
        combine(
            communityRepository.getPost(postId.value),
            postCommentsRepository.getPostComments(postId.value)
        ) { post, comments ->
            if (post == null) {
                PostCommentState.Error("Something went wrong")
            } else {
                val data = PostCommentData(
                    communityPost = post,
                    comments = comments
                )
                PostCommentState.Success(data)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PostCommentState.Loading
        )

    fun postComment(postId: String, comment: String) {
        viewModelScope.launch {
            try {
                postCommentsRepository.postComment(postId, comment)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
