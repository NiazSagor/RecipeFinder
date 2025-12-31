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
import com.example.recipefinder.data.model.Tip
import com.example.recipefinder.util.getRelativeTimeSpanString

@Composable
fun RecipeTipsListItem(tip: Tip) {
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
                        .data(tip.userProfileImageUrl)
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
                        text = tip.userName,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                    Text(
                        text = tip.timestamp.getRelativeTimeSpanString(),
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                    // tip description
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = tip.description,
                        color = Color.Black,
                        maxLines = 10,
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewRecipeTipsListItem() {
    RecipeTipsListItem(
        Tip(
            timestamp = 1736955113230,
            description = "",
            userName = "",
            userProfileImageUrl = ""
        )
    )
}

