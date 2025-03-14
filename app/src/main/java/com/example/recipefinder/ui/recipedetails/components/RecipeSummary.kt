package com.example.recipefinder.ui.recipedetails.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun RecipeSummary(
    text: String,
    collapsedMaxLines: Int = 3
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(
                animationSpec = spring(
                    stiffness = Spring.StiffnessMediumLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
            ) + fadeIn(),
            exit = shrinkVertically(
                animationSpec = spring(
                    stiffness = Spring.StiffnessMediumLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
            ) + fadeOut()
        ) {
            Text(
                text = if (isExpanded) text else text.take(200),
                maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLines,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (!isExpanded) {
            Text(
                text = text.take(200), // Show preview text when collapsed
                maxLines = collapsedMaxLines,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            color = MaterialTheme.colorScheme.primary,
            text = if (isExpanded) "See less" else "See more",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable(
                    onClick = { isExpanded = !isExpanded }
                )
                .padding(top = 4.dp)
        )
    }
}

@Composable
fun PreviewExpandableText() {

}
