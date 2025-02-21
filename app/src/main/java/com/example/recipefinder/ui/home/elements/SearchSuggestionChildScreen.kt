package com.example.recipefinder.ui.home.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
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
import com.example.recipefinder.ui.model.MealType
import com.example.recipefinder.util.mealType

@Composable
fun SearchSuggestionChildScreen(
    onTimeFilterSelected: (Int) -> Unit,
    onDishTypeSelected: (String) -> Unit,
) {
    val timeSuggestions = listOf(5, 20, 45, 60)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Popular", fontWeight = FontWeight.Bold, fontSize = 14.sp,
            )
        }

        mealType.chunked(2).forEach { pair: List<MealType> ->
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f) // Half the width of the Row
                            .height(70.dp) // Make it square
                            .background(Color.LightGray.copy(alpha = 0.3f))
                            .clickable(
                                enabled = true,
                                onClick = { onDishTypeSelected(pair.first().type) },
                                indication = remember {
                                    ripple(bounded = true, color = Color.White)
                                },
                                interactionSource = remember { MutableInteractionSource() }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        PopularRecipeSuggestionGridItem(
                            imageVector = pair.first().imageVector,
                            title = pair.first().type
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f) // Half the width of the Row
                            .height(70.dp) // Make it square
                            .background(Color.LightGray.copy(alpha = 0.3f))
                            .clickable(
                                enabled = true,
                                onClick = { onDishTypeSelected(pair.last().type) },
                                indication = remember {
                                    ripple(bounded = true, color = Color.White)
                                },
                                interactionSource = remember { MutableInteractionSource() }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        PopularRecipeSuggestionGridItem(
                            imageVector = pair.last().imageVector,
                            title = pair.last().type
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Difficulty", fontWeight = FontWeight.Bold, fontSize = 14.sp,
            )
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
                            border = BorderStroke(0.dp, Color.Transparent),
                            colors = SelectableChipColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                labelColor = Color.White,
                                leadingIconColor = Color.Black,
                                trailingIconColor = Color.Transparent,
                                disabledContainerColor = Color.LightGray,
                                disabledLabelColor = Color.Black,
                                disabledLeadingIconColor = Color.Transparent,
                                disabledTrailingIconColor = Color.Transparent,
                                selectedContainerColor = Color.LightGray,
                                disabledSelectedContainerColor = Color.LightGray,
                                selectedLabelColor = Color.Black,
                                selectedLeadingIconColor = Color.Black,
                                selectedTrailingIconColor = Color.Transparent,
                            ),
                            shape = RoundedCornerShape(16.dp),
                            onClick = {
                                selected = !selected
                                onTimeFilterSelected(timeSuggestions[time])
                            },
                            label = {
                                Text(
                                    fontWeight = FontWeight.Bold,
                                    text = "${timeSuggestions[time]} min",
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
        //contentAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(start = 32.dp),
                imageVector = imageVector,
                contentDescription = ""
            )
            //Spacer(modifier = Modifier.size(12.dp))
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                text = title.replaceFirstChar { it.uppercase() },
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
