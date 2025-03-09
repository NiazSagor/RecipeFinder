package com.example.recipefinder.ui.recipedetails.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecipeServings(
    currentServings: Int,
    onValueChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Ingredients for\n")
                }
                append("$currentServings Servings")
            }
        )

        ServingsControl(
            initialServings = currentServings,
            onValueChange = onValueChange
        )
    }
}


@Composable
fun ServingsControl(
    initialServings: Int,
    onValueChange: (Int) -> Unit
) {
    var servings by remember { mutableStateOf(initialServings) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(4.dp)
            .wrapContentWidth()
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        // minus
        IconButton(
            modifier = Modifier
                .size(28.dp)
                .padding(start = 8.dp),
            onClick = {
                if (servings > 1) {
                    servings--
                    onValueChange(servings)
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease servings",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // servings/person
        Text(
            fontSize = 12.sp,
            text = servings.toString(),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // plus icon
        IconButton(
            modifier = Modifier
                .size(28.dp)
                .padding(end = 8.dp),
            onClick = {
                servings++
                onValueChange(servings)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase servings",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
