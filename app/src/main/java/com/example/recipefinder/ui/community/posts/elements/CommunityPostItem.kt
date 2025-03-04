package com.example.recipefinder.ui.community.posts.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.recipefinder.data.model.CommunityPost
import java.util.UUID

// individual post design
@Composable
fun CommunityPostItem(
    isPostLikedByUser: suspend (String) -> Boolean,
    communityPost: CommunityPost,
    onLike: (String) -> Unit,
    onComment: (String) -> Unit,
    onClick: (String) -> Unit
) {
    var isLikedByUser by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isLikedByUser = isPostLikedByUser(communityPost.postId)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(communityPost.postId) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // post recipe image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(communityPost.recipeImageUrl)
                    .build(),
                contentDescription = "Community post image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(communityPost.userProfileImageUrl)
                        .build(),
                    contentDescription = "User profile image",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Column(

                ) {
                    Text(
                        color = Color.Black,
                        fontSize = 12.sp,
                        text = "${communityPost.userName} cooked",
                    )
                    Text(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        text = communityPost.recipeTitle,
                    )
                }
            }
            // description post
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = communityPost.post
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${communityPost.like}",
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(4.dp))

                // click on like button
                IconButton(
                    onClick = {
                        onLike(communityPost.postId) // lambda trigger
                        isLikedByUser = !isLikedByUser
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = if (isLikedByUser) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    fontSize = 12.sp,
                    text = "Comments",
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(4.dp))

                // click on comment button
                IconButton(
                    onClick = {
                        onComment(communityPost.postId) // lambda trigger -> navigate to comment screen
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Outlined.ModeComment,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCommunityPostItem() {
    CommunityPostItem(
        communityPost = CommunityPost(
            timestamp = System.currentTimeMillis(),
            post = "I recently tried the Spicy Garlic Butter Shrimp recipe, and it turned out amazing! The instructions were straightforward, making it easy to follow even as a beginner. The garlic and butter flavors perfectly complemented the shrimp, and the spice level was just right—not too mild or overwhelming. I added a splash of lemon juice, which really elevated the taste. The shrimp cooked quickly, so it’s essential not to overcook them, but the end result was juicy and flavorful. It paired beautifully with steamed veggies and rice as suggested, and my family couldn’t stop raving about how restaurant-quality it tasted. Even my picky eater went for seconds, which is rare. Cleanup was a breeze since the recipe only required one pan, and the portion size was generous enough for our family of four. While the recipe was fantastic, I had to adjust the salt to taste, so a bit more detail there would have been helpful. Overall, this dish was a winner, and I’ll definitely be making it again for both family dinners and special occasions.",
            userName = "Niaz Sagor",
            userProfileImageUrl = "",
            recipeImageUrl = "",
            recipeTitle = "Garlic Butter Shrimp",
            like = 0,
            postId = UUID.randomUUID().toString()
        ),
        onClick = {},
        onLike = {},
        onComment = {},
        isPostLikedByUser = { true }
    )
}
