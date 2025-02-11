package com.example.recipefinder.ui.community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipefinder.data.model.CommunityPost
import com.example.recipefinder.ui.community.elements.CommunityPostItem

@Composable
fun CommunityScreen(
    paddingValues: PaddingValues,
    viewmodel: CommunityScreenViewModel = hiltViewModel(),
    onPostClick: () -> Unit
) {
    val state by viewmodel.communityPosts.collectAsStateWithLifecycle()
    when (state) {
        is CommunityScreenState.Error -> {}
        CommunityScreenState.Loading -> {}
        is CommunityScreenState.Success -> {
            val posts = (state as CommunityScreenState.Success).posts
            CommunityScreenContent(
                paddingValues = paddingValues, posts = posts,
                onPostClick = { onPostClick() },
                onLike = {
                    viewmodel.likePost(it)
                },
                onComment = {

                },
            )
        }
    }
}

@Composable
private fun CommunityScreenContent(
    paddingValues: PaddingValues,
    posts: List<CommunityPost>,
    onPostClick: () -> Unit,
    onLike: (String) -> Unit = {},
    onComment: (String) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = {
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .wrapContentHeight(),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                text = "Our Community"
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = { onPostClick() },
                icon = { Icon(Icons.Filled.Add, "Post") },
                text = {
                    Text(
                        text = "Post",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                },
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .height(50.dp)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(posts) { index, post ->
                CommunityPostItem(
                    post = post,
                    onLike = { onLike(it) },
                    onComment = { onComment(it) },
                    onClick = {  },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCommunityScreen() {
    val dummyPosts = listOf(
        CommunityPost(
            timestamp = System.currentTimeMillis(),
            post = "Just tried this amazing chocolate cake recipe. It was a hit with my family!",
            userName = "Alice",
            userProfileImageUrl = "https://example.com/user1.jpg",
            recipeImageUrl = "https://example.com/recipe1.jpg",
            recipeTitle = "Chocolate Cake",
            like = 125
        ),
        CommunityPost(
            timestamp = System.currentTimeMillis(),
            post = "The pasta recipe shared here was fantastic! Perfect for a quick dinner.",
            userName = "Bob",
            userProfileImageUrl = "https://example.com/user2.jpg",
            recipeImageUrl = "https://example.com/recipe2.jpg",
            recipeTitle = "Creamy Alfredo Pasta",
            like = 89
        ),
        CommunityPost(
            timestamp = System.currentTimeMillis(),
            post = "I loved the vegan burger recipe! It's a game-changer for plant-based meals.",
            userName = "Charlie",
            userProfileImageUrl = "https://example.com/user3.jpg",
            recipeImageUrl = "https://example.com/recipe3.jpg",
            recipeTitle = "Vegan Burger",
            like = 200
        ),
        CommunityPost(
            timestamp = System.currentTimeMillis(),
            post = "Tried the smoothie recipe today, and it was so refreshing and healthy!",
            userName = "Diana",
            userProfileImageUrl = "https://example.com/user4.jpg",
            recipeImageUrl = "https://example.com/recipe4.jpg",
            recipeTitle = "Berry Smoothie",
            like = 145
        ),
        CommunityPost(
            timestamp = System.currentTimeMillis(),
            post = "The banana bread recipe is so simple yet delicious! Highly recommended.",
            userName = "Ethan",
            userProfileImageUrl = "https://example.com/user5.jpg",
            recipeImageUrl = "https://example.com/recipe5.jpg",
            recipeTitle = "Banana Bread",
            like = 310
        )
    )
    CommunityScreenContent(
        paddingValues = PaddingValues(20.dp),
        posts = dummyPosts,
        onPostClick = {},
        onLike = {},
        onComment = {}
    )
}
