package com.example.recipefinder.ui.home.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.util.toHourMinuteFormat

@Composable
fun RecipeHorizontalListItem(
    getLikesForRecipe: suspend (Int) -> Int,
    recipe: Recipe,
    searchItem: Boolean = false,
    onRecipeClick: (Int) -> Unit,
    onSave: (Recipe) -> Unit,
) {
    var likes by remember { mutableIntStateOf(0) }
    var isSaved by remember { mutableStateOf(recipe.isBookmarked) }
    LaunchedEffect(recipe) {
        likes = getLikesForRecipe(recipe.id)
    }

    val itemModifier = if (searchItem) {
        Modifier
            .wrapContentHeight()
            .width(170.dp)
    } else {
        Modifier.size(width = 170.dp, height = 260.dp)
    }
    Box(
        modifier = Modifier
            .then(itemModifier)
            .clickable(
                enabled = true,
                onClick = { onRecipeClick(recipe.id) },
                indication = remember {
                    ripple(bounded = true, color = Color.White)
                },
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        Column(

        ) {
            Box(
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(recipe.image)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 12.dp
                            )
                        )
                        .background(MaterialTheme.colorScheme.secondary)
                ) {
                    OneIconAndOneText(
                        modifier = Modifier
                            .size(width = 30.dp, height = 20.dp)
                            .align(Alignment.Center),
                        recipe.extendedIngredients.size,
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .size(35.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(32.dp))
                        .align(Alignment.BottomEnd)
                        .wrapContentSize()
                        .clickable(
                            enabled = true,
                            onClick = {
                                onSave(recipe)
                                isSaved = !isSaved
                            },
                        )
                ) {
                    Icon(
                        imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    color = Color.Black,
                    text = recipe.readyInMinutes.toHourMinuteFormat(),
                    fontSize = 12.sp
                )
                Icon(
                    Icons.Default.ThumbUp,
                    contentDescription = "",
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "$likes",
                    fontSize = 12.sp
                )
            }
            Text(
                modifier = Modifier.padding(bottom = 8.dp, end = 8.dp),
                text = recipe.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeHorizontalListItem() {

}
