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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.util.toHourMinuteFormat

@Composable
fun RecipeHorizontalListItem(
    recipe: Recipe,
    onRecipeClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .size(width = 170.dp, height = 300.dp)
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
                    model = recipe.image,
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
                Text(
                    text = recipe.readyInMinutes.toHourMinuteFormat(),
                    fontSize = 12.sp
                )
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
                text = recipe.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeHorizontalListItem() {

}
