package com.example.recipefinder.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.ui.home.components.BottomNavigationBar
import com.example.recipefinder.ui.home.elements.RecipeHorizontalListItem
import com.example.recipefinder.ui.home.elements.TopContainer
import com.example.recipefinder.ui.home.elements.TopRecipeCard

@Composable
fun Home(

) {

}

@Composable
fun HomeContent(

) {
    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 170.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = paddingValues.calculateBottomPadding()
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                TopRecipeCard()
            }

            item {
                HorizontalList("Trending")
            }

            item {
                HorizontalList("Popular Recipes This Week")
            }

            item {
                HorizontalList("Desserts")
            }
        }


        TopContainer(Modifier.padding(paddingValues))
    }
}

@Composable
fun HorizontalList(
    title: String,
) {
    Column(

    ) {
        Text(
            text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(15) {
                RecipeHorizontalListItem()
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewHomeContent() {
    HomeContent()
}

@Preview(showBackground = true)
@Composable
fun PreviewHorizontalTrendingList() {
    HorizontalList("Trending")
}