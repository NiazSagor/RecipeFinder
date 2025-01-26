package com.example.recipefinder.ui.community.elements

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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.runtime.Composable
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
import com.example.recipefinder.R
import com.example.recipefinder.data.model.CommunityPost
import java.util.UUID


@Composable
fun CommunityPostItem(
    post: CommunityPost,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(post.postId) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.ic_launcher_background)
                    .build(),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // TODO: user profile pic url
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.ic_launcher_background)
                        .build(),
                    contentDescription = "",
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
                        text = "${post.userName} cooked",
                    )
                    Text(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary,
                        text = post.recipeTitle,
                    )
                }
            }
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = post.post
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${post.like}",
                    color = MaterialTheme.colors.primary
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Outlined.Favorite,
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    fontSize = 12.sp,
                    text = "Comments",
                    color = MaterialTheme.colors.primary
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Outlined.ModeComment,
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCommunityPostItem() {
    CommunityPostItem(
        post = CommunityPost(
            timestamp = System.currentTimeMillis(),
            post = "I recently tried the Spicy Garlic Butter Shrimp recipe, and it turned out amazing! The instructions were straightforward, making it easy to follow even as a beginner. The garlic and butter flavors perfectly complemented the shrimp, and the spice level was just right—not too mild or overwhelming. I added a splash of lemon juice, which really elevated the taste. The shrimp cooked quickly, so it’s essential not to overcook them, but the end result was juicy and flavorful. It paired beautifully with steamed veggies and rice as suggested, and my family couldn’t stop raving about how restaurant-quality it tasted. Even my picky eater went for seconds, which is rare. Cleanup was a breeze since the recipe only required one pan, and the portion size was generous enough for our family of four. While the recipe was fantastic, I had to adjust the salt to taste, so a bit more detail there would have been helpful. Overall, this dish was a winner, and I’ll definitely be making it again for both family dinners and special occasions.",
            userName = "Niaz Sagor",
            userProfileImageUrl = "",
            recipeImageUrl = "",
            recipeTitle = "Garlic Butter Shrimp",
            like = 0,
            postId = UUID.randomUUID().toString()
        ),
        onClick = {}
    )
}
