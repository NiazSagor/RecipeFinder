package com.example.recipefinder.ui.recipedetails.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    isRecipeBookMarked: Boolean,
    onLike: () -> Unit,
    onSave: () -> Unit,
    onPopCurrent: () -> Unit,
    title: String,
    scrollBehavior: TopAppBarScrollBehavior
) {
    var isLiked by remember { mutableStateOf(false) }
    var isSaved by remember { mutableStateOf(isRecipeBookMarked) }

    LargeTopAppBar(
        title = {
            // recipe title
            Text(
                text = title,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        },
        // back icon
        navigationIcon = {
            IconButton(onClick = { onPopCurrent() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Back to the Home",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            // save recipe
            IconButton(onClick = {
                onSave()
                isSaved = !isSaved
            }) {
                Icon(
                    imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            // like recipe button
            IconButton(onClick = {
                isLiked = !isLiked
                onLike()
            }) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                    contentDescription = "Like",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewTopBar() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    TopBar(
        isRecipeBookMarked = false,
        onLike = {},
        onSave = {},
        title = "Nacho Lasagna Pasta Chips",
        onPopCurrent = {},
        scrollBehavior = scrollBehavior
    )
}
