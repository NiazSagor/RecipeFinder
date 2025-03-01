package com.example.recipefinder.data.repository.community

import com.example.recipefinder.data.model.PostComment
import kotlinx.coroutines.flow.Flow

interface PostCommentsRepository {
    fun getPostComments(postId: String): Flow<List<PostComment>>
    suspend fun postComment(postId: String, comment: String)
}
