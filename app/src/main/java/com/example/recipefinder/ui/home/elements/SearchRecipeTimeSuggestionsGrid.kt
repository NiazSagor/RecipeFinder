package com.example.recipefinder.ui.home.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SearchRecipeTimeSuggestionsGrid(
    onTimeFilterSelected: (Int) -> Unit, // time filter select
    onDishTypeSelected: (String) -> Unit, // meal type select
    onSearchTypeChanged: (String) -> Unit // switch
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SearchSuggestionChildScreen(
            onTimeFilterSelected = onTimeFilterSelected,
            onSearchTypeChanged = onSearchTypeChanged,
            onMealTypeSelected = onDishTypeSelected
        )
    }
}
