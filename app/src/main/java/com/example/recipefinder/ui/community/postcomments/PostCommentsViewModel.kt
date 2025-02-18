package com.example.recipefinder.ui.community.postcomments

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.CommunityPost
import com.example.recipefinder.data.model.PostComment
import com.example.recipefinder.data.repository.community.CommunityRepository
import com.example.recipefinder.data.repository.community.PostCommentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _postComments = MutableStateFlow<PostCommentState>(PostCommentState.Loading)

    val postComments = _postComments.asStateFlow()

    fun getPostComments(postId: String) {
        viewModelScope.launch {
            _postComments.value = PostCommentState.Loading
            try {
                val post = communityRepository.getPost(postId)
                val comments = postCommentsRepository.getPostComments(postId)
                val postCommentData = PostCommentData(post!!, comments)
                _postComments.value = PostCommentState.Success(postCommentData)
            } catch (e: Exception) {
                _postComments.value = PostCommentState.Error(e.message ?: "Unknown error")
                e.printStackTrace()
            }
        }
    }

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
