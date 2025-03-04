package com.example.recipefinder.data.repository.community.post

import android.net.Uri
import com.example.recipefinder.data.model.CommunityPost
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the contract for interacting with the community data source.
 * This repository handles fetching, retrieving, and manipulating community posts and their associated data.
 */
interface CommunityRepository {
    /**
     * Retrieves a flow of community posts.
     *
     * This function fetches a list of [CommunityPost] objects representing posts from the community.
     * It returns a [Flow] that emits a new list whenever the underlying data source changes.
     * This allows for reactive UI updates as new posts are added or existing ones are modified.
     *
     * The emitted lists are expected to be up-to-date snapshots of the community's posts at the time of emission.
     *
     * Note: The specific implementation of how the posts are fetched (e.g., from a local database, a network API)
     * is abstracted away by this function, providing a consistent way to access community post data.
     *
     * @return A [Flow] of [List<CommunityPost>], where each list represents the current set of community posts.
     */
    fun getCommunityPosts(): Flow<List<CommunityPost>>

    /**
     * Retrieves a community post by its ID.
     *
     * This function fetches a single community post from the data source based on the provided [postId].
     * It returns a [Flow] that emits the post once it's available or null if no post with the given ID exists.
     *
     * The [Flow] can emit:
     *   - A [CommunityPost] object if a post with the matching ID is found.
     *   - null if no post with the given ID is found.
     *
     * The flow will complete after emitting the result.
     *
     * @param postId The unique identifier of the community post to retrieve. Must not be blank.
     * @return A [Flow] that emits the requested [CommunityPost] or null.
     * @throws IllegalArgumentException if [postId] is blank.
     */
    fun getPost(postId: String): Flow<CommunityPost?>

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

    /**
     * Posts a new recipe to the server or a data storage.
     *
     * This function handles the submission of a new recipe, including its textual description,
     * title, and an associated image. It's designed to be a suspend function, making it suitable
     * for coroutines and asynchronous operations, especially when dealing with network requests or
     * time-consuming tasks like image uploads.
     *
     * @param post The textual content of the recipe. This could be the recipe instructions,
     *             ingredients, or any other relevant description. Must not be empty.
     * @param recipeTitle The title of the recipe. Must not be empty.
     * @param recipeImageUri The URI of the image associated with the recipe. This could be a
     *                       local file URI or a content URI. It can be null if there's no image.
     *
     * @throws IllegalArgumentException if either `post` or `recipeTitle` is empty.
     * @throws Exception if any other error occurs during the posting process (e.g., network error,
     *                   image upload failure).
     *
     * @see Uri
     */
    suspend fun postRecipe(post: String, recipeTitle: String, recipeImageUri: Uri)
}
