package com.example.recipefinder.ui.home.elements

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefinder.data.model.SearchRecipeByIngredients
import com.example.recipefinder.ui.home.HomeViewModel
import com.example.recipefinder.ui.home.components.SearchBar
import com.example.recipefinder.ui.home.components.SearchDisplay
import com.example.recipefinder.ui.home.components.rememberSearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TopContainer(
    bottomPadding: Dp,
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier,
    onRecipeClick: (Int) -> Unit
) {
    val selectedTimeFilter = remember { mutableStateOf(Int.MAX_VALUE) }
    val state =
        rememberSearchState(
            initialResults = emptyList<SearchRecipeByIngredients>(),
            suggestions = emptyList<SearchRecipeByIngredients>(),
            timeoutMillis = 5000
        ) { query: TextFieldValue ->
            withContext(Dispatchers.IO) {
                Log.e("HomeScreenViewModel", "TopContainer: ${query.text}")
                viewModel.getSearchResult(query.text, selectedTimeFilter.value)
            }
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

            when (state.searchDisplay) {
                SearchDisplay.InitialResults -> {}
                SearchDisplay.Suggestions -> {
                    SearchRecipeTimeSuggestionsGrid {
                        selectedTimeFilter.value = it
                    }
                }

                SearchDisplay.SearchInProgress -> {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .fillMaxSize()
                    )
                }

                SearchDisplay.Results -> {
                    SearchResultStaggeredGrid(
                        modifier = Modifier.padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 8.dp,
                            bottom = bottomPadding
                        ),
                        state.searchResults
                    ) {
                        onRecipeClick(it)
                    }
                }

                SearchDisplay.NoResults -> {
                    Box(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("❌ No Results!", fontSize = 24.sp, color = Color(0xffDD2C00))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopContainer() {
}
