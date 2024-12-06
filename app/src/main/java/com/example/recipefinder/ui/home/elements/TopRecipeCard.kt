package com.example.recipefinder.ui.home.elements

import androidx.compose.foundation.background

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.recipefinder.data.model.Recipe

@Composable
fun TopRecipeCard(
    onRecipeClick: (Int) -> Unit,
    recipe: Recipe
) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(500.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable(
                enabled = true,
                onClick = { onRecipeClick(recipe.id) },
                indication = remember {
                    ripple(bounded = true, color = Color.White)
                },
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(recipe.image)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.FillHeight,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.secondary)
                .padding(16.dp)
        ) {
            Column(

            ) {
                Row(
                ) {
//                    Icon(
//                        Icons.Default.Check, contentDescription = "",
//                        modifier = Modifier.size(16.dp)
//                    )
                    Text(
                        text = "${recipe.readyInMinutes} mins",
                        fontSize = 12.sp
                    )
                }
                Text(
                    color = Color.Black,
                    text = recipe.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTopRecipeCard() {

}
