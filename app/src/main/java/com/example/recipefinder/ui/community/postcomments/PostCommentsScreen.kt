package com.example.recipefinder.ui.community.postcomments

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.example.recipefinder.data.model.PostComment
import com.example.recipefinder.data.model.Tip
import com.example.recipefinder.ui.community.posts.elements.CommunityPostItem
import com.example.recipefinder.ui.recipetipdetails.components.RecipeTipsListItem

// shows all comments on a post
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PostCommentsScreen(
    viewmodel: PostCommentsViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    postId: String
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()
    when (state) {
        is PostCommentState.Error -> {}
        PostCommentState.Loading -> {}
        is PostCommentState.Success -> {
            val data = (state as PostCommentState.Success).data
            Scaffold(
                modifier = Modifier
                    .padding(paddingValues),
                topBar = {
                    Text(
                        text = "Comments",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .wrapContentHeight(),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
            ) { paddingValues ->
                PostCommentsScreenContent(
                    paddingValues = paddingValues,
                    data = data,
                    onPostClick = {
                        viewmodel.postComment(postId = postId, comment = it)
                    },
                    isPostLikedByUser = {
                        viewmodel.isPostLikedByUser(it)
                    }
                )
            }
        }
    }
}

@Composable
fun PostCommentsScreenContent(
    paddingValues: PaddingValues,
    data: PostCommentData,
    isPostLikedByUser: suspend (String) -> Boolean,
    onPostClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // image. title. description
            item {
                CommunityPostItem(
                    communityPost = data.communityPost,
                    onLike = {},
                    onComment = {},
                    onClick = {},
                    isPostLikedByUser = isPostLikedByUser
                )
            }

            // post comment section
            itemsIndexed(data.comments) { index: Int, comment: PostComment ->
                Box(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                ) {
                    RecipeTipsListItem(
                        Tip(
                            timestamp = comment.timestamp,
                            tip = comment.comment,
                            userName = comment.userName,
                            userProfileImageUrl = comment.userProfileImageUrl
                        )
                    )
                }
            }
        }
        CommentInputField(
            onPostClick = {
                onPostClick(it)
            }
        )
    }
}


@Composable
fun CommentInputField(
    onPostClick: (String) -> Unit
) {
    var commentText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = commentText,
                onValueChange = { commentText = it },
                placeholder = { Text(text = "Add a comment...", fontSize = 12.sp) },
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    ),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Button(
                onClick = {
                    if (commentText.isNotBlank()) {
                        onPostClick(commentText)
                        commentText = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.height(48.dp)
            ) {
                Text(text = "Post", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRecipeCommentsScreen() {
    PostCommentsScreenContent(
        data = PostCommentData(
            communityPost = CommunityPost(
                recipeTitle = "Fluffy Pancakes",
                like = 125,
                timestamp = 1736955113230,
                post = "Just tried this amazing chocolate cake recipe. It was a hit with my family!",
                userName = "Niaz Sagor",
                userProfileImageUrl = "",
                recipeImageUrl = "",
            ),
            comments = listOf(
                PostComment(
                    timestamp = 1736955113230,
                    comment = "Yes Gabi, let's support God and Jesus! Gabi, you are so slay.",
                    userName = "Niaz Sagor",
                    userProfileImageUrl = "",
                    postId = ""
                ),
                PostComment(
                    timestamp = 1736955120000,
                    comment = "Amazing post! Totally agree with you.",
                    userName = "John Doe",
                    userProfileImageUrl = "",
                    postId = ""
                ),
                PostComment(
                    timestamp = 1736955135000,
                    comment = "This is exactly what I was thinking. Well said!",
                    userName = "Jane Smith",
                    userProfileImageUrl = "",
                    postId = ""
                ),
                PostComment(
                    timestamp = 1736955148000,
                    comment = "Love this! Keep spreading positivity.",
                    userName = "Emily Carter",
                    userProfileImageUrl = "",
                    postId = ""
                ),
                PostComment(
                    timestamp = 1736955160000,
                    comment = "Respect! You nailed it with this one.",
                    userName = "Michael Johnson",
                    userProfileImageUrl = "",
                    postId = ""
                )
            )
        ),
        onPostClick = {},
        paddingValues = PaddingValues(),
        isPostLikedByUser = { false }
    )
}
