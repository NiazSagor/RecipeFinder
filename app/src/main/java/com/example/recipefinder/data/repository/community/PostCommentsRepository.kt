package com.example.recipefinder.data.repository.community

import com.example.recipefinder.data.model.PostComment

interface PostCommentsRepository {
    suspend fun getPostComments(postId: String): List<PostComment>
    suspend fun postComment(postId: String, comment: String)
}
