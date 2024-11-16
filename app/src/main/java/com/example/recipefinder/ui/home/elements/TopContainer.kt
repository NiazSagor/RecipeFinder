package com.example.recipefinder.ui.home.elements

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.ui.home.components.SearchBar
import com.example.recipefinder.ui.home.components.rememberSearchState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TopContainer(
    modifier: Modifier
) {
    val state =
        rememberSearchState(
            initialResults = emptyList<Recipe>(),
            suggestions = emptyList<Recipe>(),
            timeoutMillis = 600
        ) { query: TextFieldValue ->
            emptyList<Recipe>()
        }
    Box(
        modifier = modifier
            .fillMaxWidth()
//            .height(170.dp)
            .background(Color.White)
            .padding(vertical = 16.dp, horizontal = 0.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //Text(text = "RecipeFinder", fontSize = 22.sp, fontStyle = FontStyle.Normal)
            //Spacer(modifier = Modifier.height(16.dp))
            SearchBar(
                query = state.query,
                onQueryChange = { state.query = it },
                onSearchFocusChange = { state.focused = it },
                onClearQuery = { state.query = TextFieldValue("") },
                onBack = { state.query = TextFieldValue("") },
                searching = state.searching,
                focused = state.focused,
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopContainer() {
    TopContainer(Modifier)
}
