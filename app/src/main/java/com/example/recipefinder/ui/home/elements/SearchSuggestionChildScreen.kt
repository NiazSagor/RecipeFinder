package com.example.recipefinder.ui.home.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.ui.home.components.Breakfast_dining
import com.example.recipefinder.ui.home.components.Dessert
import com.example.recipefinder.ui.home.components.Dinner_dining
import com.example.recipefinder.ui.home.components.Fastfood
import com.example.recipefinder.ui.home.components.Food_bank
import com.example.recipefinder.ui.home.components.Lunch_dining

@Composable
fun SearchSuggestionChildScreen(onSelected: (Int) -> Unit) {
    val popularSuggestionList =
        listOf("Dinner", "Breakfast", "Desserts", "Lunch", "Main Course", "Snack")

    val timeSuggestions = listOf(5, 20, 45, 60)

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = "Popular", fontWeight = FontWeight.Bold, fontSize = 14.sp,
            )
        }

        item {
            Spacer(modifier = Modifier.size(8.dp))
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f) // Half the width of the Row
                        .height(70.dp) // Make it square
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    PopularRecipeSuggestionGridItem(
                        Dinner_dining,
                        popularSuggestionList[0]
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Box(
                    modifier = Modifier
                        .weight(1f) // Half the width of the Row
                        .height(70.dp) // Make it square
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    PopularRecipeSuggestionGridItem(
                        Breakfast_dining,
                        popularSuggestionList[1]
                    )
                }

            }
        }

        item {
            Spacer(modifier = Modifier.size(16.dp))
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f) // Half the width of the Row
                        .height(70.dp) // Make it square
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    PopularRecipeSuggestionGridItem(
                        Dessert,
                        popularSuggestionList[2]
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Box(
                    modifier = Modifier
                        .weight(1f) // Half the width of the Row
                        .height(70.dp) // Make it square
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    PopularRecipeSuggestionGridItem(
                        Lunch_dining,
                        popularSuggestionList[3]
                    )
                }

            }
        }

        item {
            Spacer(modifier = Modifier.size(16.dp))
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f) // Half the width of the Row
                        .height(70.dp) // Make it square
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    PopularRecipeSuggestionGridItem(
                        Food_bank,
                        popularSuggestionList[4]
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Box(
                    modifier = Modifier
                        .weight(1f) // Half the width of the Row
                        .height(70.dp) // Make it square
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    PopularRecipeSuggestionGridItem(
                        Fastfood,
                        popularSuggestionList[5]
                    )
                }

            }
        }

        item {
            Spacer(modifier = Modifier.size(16.dp))
        }

        item {
            Text(
                text = "Difficulty", fontWeight = FontWeight.Bold, fontSize = 14.sp,
            )
        }

        item {
            Spacer(modifier = Modifier.size(8.dp))
        }

        item {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                columns = StaggeredGridCells.Adaptive(100.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    items(timeSuggestions.size) { time ->
                        var selected by remember { mutableStateOf(false) }
                        FilterChip(
                            shape = RoundedCornerShape(16.dp),
                            onClick = {
                                selected = !selected
                                onSelected(timeSuggestions[time])
                            },
                            label = {
                                Text(
                                    text = "${timeSuggestions[time]} min",
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                            selected = selected,
                            leadingIcon = if (selected) {
                                {
                                    Icon(
                                        imageVector = Icons.Filled.Done,
                                        contentDescription = "Done icon",
                                        modifier = Modifier.size(FilterChipDefaults.IconSize),
                                    )
                                }
                            } else {
                                null
                            },
                        )
                    }
                }
            )
        }

    }
}


@Composable
fun PopularRecipeSuggestionGridItem(
    imageVector: ImageVector,
    title: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 12.sp
            )
        }
    }
}
