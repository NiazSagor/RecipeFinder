package com.example.recipefinder.ui.recipetipdetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
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

@Composable
fun RecipeTipsListItem(

) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

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
                        text = "Jonathon O",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "12 Dec 2024",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "this is a comment",
                        color = Color.Black,
                        maxLines = 4,
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewRecipeTipsListItem() {
    RecipeTipsListItem()
}

