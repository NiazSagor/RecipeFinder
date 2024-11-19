package com.example.recipefinder.ui.home.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.recipefinder.data.model.SearchRecipeByIngredients

@Composable
fun SearchResultStaggeredListItem(
    recipeResult: SearchRecipeByIngredients,
    onRecipeClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = 150.dp, height = 250.dp)
            .clickable(
                enabled = true,
                onClick = { onRecipeClick(recipeResult.id) },
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
                    model = recipeResult.image,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 12.dp
                            )
                        )
                        .background(Color.Cyan)
                ) {
                    OneIconAndOneText(
                        modifier = Modifier
                            .size(width = 30.dp, height = 20.dp)
                            .align(Alignment.Center)
                    )
                }
            }
            Row(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Text(
//                    text = "39 mins •",
//                    fontSize = 12.sp
//                )
//                Icon(
//                    Icons.Default.ThumbUp,
//                    contentDescription = "",
//                    modifier = Modifier.size(16.dp)
//                )
//                Text(
//                    text = "98 %",
//                    fontSize = 12.sp
//                )
            }
            Text(
                modifier = Modifier.padding(bottom = 8.dp, end = 8.dp),
                text = recipeResult.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
        }
    }
}
