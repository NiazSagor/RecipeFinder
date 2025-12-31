package com.example.recipefinder.data.repository.tip

import com.example.recipefinder.data.model.Tip
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing recipe tips.
 *
 * This interface defines the contract for interacting with the data source
 * responsible for storing and retrieving tips associated with recipes.
 */
interface RecipeTipsRepository {

    /**
     * Retrieves all tips associated with a specific recipe.
     *
     * This function fetches a list of [Tip] objects that are related to the recipe identified by [recipeId].
     * The results are emitted as a [Flow], allowing for asynchronous and reactive data handling.
     *
     * @param recipeId The unique identifier of the recipe for which to retrieve tips.
     * @return A [Flow] that emits a list of [Tip] objects. The list may be empty if no tips are found for the given recipe.
     *         The Flow will emit a new list of tips whenever the underlying data changes.
     */
    fun getAllTipsForRecipe(recipeId: Int): Flow<List<Tip>>

    /**
     * Retrieves the number of likes for a specific recipe.
     *
     * This function interacts with a data source (e.g., a database or API) to fetch
     * the like count associated with the given recipe ID.
     *
     * @param recipeId The unique identifier of the recipe for which to retrieve likes.
     * @return The number of likes the specified recipe has received.
     * @throws Exception if there is an error retrieving the likes (e.g., network issue, database error, recipe not found).
     * The type of exception will vary depending on the underlying implementation.
     */
    suspend fun getLikesForRecipe(recipeId: Int): Int

    /**
     * Sends a tip for a specific recipe.
     *
     * This function handles the logic of sending a user-generated tip to the server,
     * potentially including an associated photo. It's a suspend function, meaning it
     * should be called within a coroutine or another suspending function.
     *
     * @param recipeId The ID of the recipe for which the tip is being submitted.
     *                 This ID should correspond to a valid recipe in the system.
     * @param tip The Tip object containing the details of the tip, such as the
     *            textual content, user information, and any other relevant data.
     *            Must not be null.
     *
     * @throws Exception if there is an error during the tip sending process, such as
     *                    network connectivity issues, server errors, or invalid input.
     *                    Specific exceptions might include:
     *                      - IOException for network related errors.
     *                      - HttpException for HTTP errors like 404 or 500.
     *                      - IllegalArgumentException if recipeId is invalid or the tip object has issues.
     */
    suspend fun sendTip(recipeId: Int, tip: Tip)

    /**
     * Likes a recipe with the specified ID.
     *
     * This function sends a request to the server to register a "like" for the given recipe.
     * The exact behavior of liking a recipe (e.g., adding it to a user's liked list,
     * incrementing a like counter, etc.) is determined by the server-side implementation.
     *
     * This is a suspend function, meaning it should be called from within a coroutine or
     * another suspend function. It may suspend execution until the like request is complete.
     *
     * @param recipeId The ID of the recipe to like. Must be a positive integer representing a valid recipe.
     * @throws Exception If an error occurs during the like operation (e.g., network issue, invalid recipe ID).
     *     Specific exception types may vary depending on the underlying implementation.
     */
    suspend fun like(recipeId: Int)
}
