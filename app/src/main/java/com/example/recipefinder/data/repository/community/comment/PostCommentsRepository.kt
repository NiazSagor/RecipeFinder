package com.example.recipefinder.data.repository.community.comment

import com.example.recipefinder.data.model.PostComment
import kotlinx.coroutines.flow.Flow

/**
 * Interface for managing post comments.
 * This repository provides methods to retrieve comments for a specific post and to submit new comments.
 */
interface PostCommentsRepository {
    /**
     * Retrieves a flow of comments associated with a specific post.
     *
     * This function fetches all comments related to the given `postId` and returns them
     * as a [Flow] of [List<PostComment>]. The flow can emit multiple lists of comments over time,
     * reflecting changes in the underlying data source (e.g., database updates, network responses).
     *
     * @param postId The unique identifier of the post for which to retrieve comments.
     *               Must be a non-empty string.
     * @return A [Flow] emitting a [List] of [PostComment] objects. Each list represents a snapshot of the comments for the specified post.
     *         The flow can emit multiple lists, potentially representing updates to the comments.
     *         If no comments are found for the given postId, an empty list will be emitted.
     *         The flow will typically complete once all data has been emitted.
     * @throws IllegalArgumentException if postId is an empty string.
     */
    fun getPostComments(postId: String): Flow<List<PostComment>>

    /**
     * Posts a comment to a specific post.
     *
     * This function sends a comment to the server associated with the provided post ID.
     * It's a suspending function, meaning it should be called within a coroutine or another
     * suspending function.
     *
     * @param postId The ID of the post to which the comment should be added. This ID uniquely
     *               identifies the target post on the server.
     * @param comment The text content of the comment to be posted. This is the message
     *                that will be associated with the comment.
     *
     * @throws Exception if there's an error during the network request or comment posting process.
     *                  Specific exceptions may vary based on the underlying implementation (e.g.,
     *                  IOException for network errors, ServerException for server-side issues).
     */
    suspend fun postComment(postId: String, comment: String)
}
