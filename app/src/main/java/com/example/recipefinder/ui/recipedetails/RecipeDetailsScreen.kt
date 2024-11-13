package com.example.recipefinder.ui.recipedetails

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.ui.recipedetails.components.PreparationTimeLine
import com.example.recipefinder.ui.recipedetails.components.RecipeIngredientsVerticalListItem
import com.example.recipefinder.ui.recipedetails.components.RecipePreparationBottomSheet
import com.example.recipefinder.ui.recipedetails.components.TopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    onPopCurrent: () -> Unit,
    title: String
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = { TopBar(onPopCurrent, title, scrollBehavior) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { if (!openBottomSheet) openBottomSheet = true },
                icon = { Icon(Icons.Filled.PlayArrow, "Start Cooking") },
                text = {
                    Text(
                        text = "Start Cooking",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                },
                shape = RoundedCornerShape(32.dp)
            )
        }
    ) { paddingValues ->
        if (openBottomSheet) {
            RecipePreparationBottomSheet(
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
                onDismissRequest = {
                    openBottomSheet = false
                }
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        ) {
            item {
                Text(
                    text =
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                            "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                )
            }

            item {
                Spacer(modifier = Modifier.size(16.dp))
            }
            item {
                Text(
                    text = "Preparation",
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                Spacer(modifier = Modifier.size(16.dp))
            }
            item {
                PreparationTimeLine()
            }
            item {
                Spacer(modifier = Modifier.size(16.dp))
            }
            item {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Ingredients for\n")
                        }
                        append("17 Servings")
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.size(16.dp))
            }
            items(15) { index ->
                RecipeIngredientsVerticalListItem()
                if (index < 14) {
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsScreen() {
    RecipeDetailsScreen({}, "Nacho Lasagna Pasta Chips")
}
