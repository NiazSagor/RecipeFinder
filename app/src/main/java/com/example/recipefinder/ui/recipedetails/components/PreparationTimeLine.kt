package com.example.recipefinder.ui.recipedetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PreparationTimeLine(

) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(3) {
                PreparationTitleAndTime()
            }
        }
    }
}


@Composable
fun PreparationTitleAndTime(

) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Total Time",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
        Text(
            text = "45 min"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPreparationTimeLine() {
    PreparationTimeLine()
}
