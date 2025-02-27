package com.example.recipefinder.ui.profile.activity;


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.ui.home.HorizontalList

@Composable
fun ActivityScreen(
    paddingValues: PaddingValues,
    myRatings: List<Recipe>,
    myTips: List<Recipe>,
    getLikesForRecipe: suspend (Int) -> Int,
    onRecipeClick: (Int) -> Unit,
    onSave: (Recipe) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding(), start = 16.dp, end = 16.dp),
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalList(
                    getLikesForRecipe = { getLikesForRecipe(it) },
                    onRecipeClick = onRecipeClick,
                    onSave = { },
                    title = "My Ratings (${myRatings.size})",
                    recipes = myRatings
                )
                if (myRatings.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            tint = MaterialTheme.colorScheme.primary,
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = null
                        )

                        Text(
                            fontSize = 12.sp,
                            text = "Rate your first recipe to see it here."
                        )
                    }
                }
            }
        }
        item {
            HorizontalList(
                getLikesForRecipe = { getLikesForRecipe(it) },
                onRecipeClick = onRecipeClick,
                onSave = { },
                title = "My Tips (${myTips.size})",
                recipes = myTips
            )
            if (myTips.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.primary,
                        imageVector = Icons.Default.Book,
                        contentDescription = null
                    )

                    Text(
                        fontSize = 12.sp,
                        text = "Leave your first tip to see it here."
                    )
                }
            }
        }
    }
}
