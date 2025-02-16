package com.example.recipefinder.data.repository.community

import com.example.recipefinder.data.model.PostComment
import javax.inject.Inject

class PostCommentsRepositoryImpl @Inject constructor(

) : PostCommentsRepository {

    override suspend fun getPostComments(postId: String): List<PostComment> {
        return emptyList()
    }

    override suspend fun postComment(postId: String, comment: String) {

    }
}
