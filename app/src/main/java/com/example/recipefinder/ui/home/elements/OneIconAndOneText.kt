package com.example.recipefinder.ui.home.elements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.ui.home.components.Cooking

@Composable
fun OneIconAndOneText(
    modifier: Modifier = Modifier,
    ingredients: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Cooking, contentDescription = "",
            modifier = modifier.size(12.dp)
        )
        Text(
            text = "$ingredients ingredients",
            fontSize = 11.sp,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}
