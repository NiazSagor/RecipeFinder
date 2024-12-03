package com.example.recipefinder.ui.recipedetails.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis


@Composable
fun ExpandableText(
    text: String,
    collapsedMaxLines: Int = 3
) {
    var isExpanded by remember { mutableStateOf(false) }
    val displayText = if (isExpanded) text else text.take(200)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(durationMillis = 500)
            )
    ) {
        Text(
            text = displayText,
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLines,
            overflow = Ellipsis
        )

        Text(
            text = if (isExpanded) "See less" else "See more",
            fontWeight = Bold,
            modifier = Modifier
                .clickable { isExpanded = !isExpanded }
                .padding(top = 4.dp)
        )
    }
}


@Composable
fun PreviewExpandableText() {

}
