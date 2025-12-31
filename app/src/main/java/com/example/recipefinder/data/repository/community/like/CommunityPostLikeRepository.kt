package com.example.recipefinder.data.repository.community.like

/**
 * Repository interface for managing likes on community posts.
 * This interface provides methods to check if a post is liked by the current user
 * and to like a post.
 */
interface CommunityPostLikeRepository {
    /**
     * Checks if a post is liked by the currently authenticated user.
     *
     * This function asynchronously determines whether the user, based on their
     * current authentication state, has liked a specific post identified by its
     * unique ID.
     *
     * @param postId The unique identifier of the post to check.
     * @return `true` if the post is liked by the user, `false` otherwise.
     *         The function returns a Boolean wrapped within a suspending function context.
     *         This implies that the result will be available when the asynchronous operation completes.
     * @throws Exception If there is an issue during data retrieval or if the user is not authenticated.
     *                   Specific exception types might include network errors or database access issues.
     */
    suspend fun isPostLikedByUser(postId: String): Boolean

    /**
     * Likes a post with the given ID.
     *
     * This function sends a request to the server to register a "like" for the specified post.
     * It is a suspending function, which means it should be called from a coroutine or another suspending function.
     *
     * @param postId The unique identifier of the post to like.
     * @throws Exception if any error occurs during the network request or processing. This could be a general exception,
     *          or more specific exceptions like IOException or HttpException depending on the implementation.
     */
    suspend fun likePost(postId: String)
}
