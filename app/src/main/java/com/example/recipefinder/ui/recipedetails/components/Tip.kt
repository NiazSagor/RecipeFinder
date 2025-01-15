package com.example.recipefinder.ui.recipedetails.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
fun Tip(
    onTipClick: () -> Unit,
    onTipDetailsClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
//            Text(
//                text = "Tips (15)",
//                fontWeight = FontWeight.Bold,
//                color = Color.Black,
//                fontSize = 14.sp
//            )

//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 10.dp),
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(R.drawable.ic_launcher_background)
//                        .build(),
//                    contentDescription = "",
//                    modifier = Modifier
//                        .size(40.dp)
//                        .clip(CircleShape),
//                    contentScale = ContentScale.Crop
//                )
//
//                Column(
//
//                ) {
//                    Text(
//                        text = "Top Tip",
//                        fontSize = 12.sp
//                    )
//                    Text(
//                        text = "Jonathon O",
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black,
//                        fontSize = 14.sp
//                    )
//                }
//            }

//            Text(
//                text = "this is a comment",
//                color = Color.Black,
//                maxLines = 4,
//            )

            Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = "See all tips and photos",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable(
                        onClick = { onTipDetailsClick() }
                    )
                )

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Button(
                onClick = { onTipClick() },
                shape = RoundedCornerShape(4),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(40.dp)
            ) {
                Text(
                    text = "Leave a tip", color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTip() {
    Tip(
        onTipClick = {},
        onTipDetailsClick = {}
    )
}
