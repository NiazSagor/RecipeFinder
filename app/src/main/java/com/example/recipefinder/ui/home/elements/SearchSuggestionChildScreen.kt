package com.example.recipefinder.ui.home.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.ui.home.components.Breakfast_dining
import com.example.recipefinder.ui.model.MealType
import com.example.recipefinder.util.mealType

@Composable
fun SearchSuggestionChildScreen(
    onTimeFilterSelected: (Int) -> Unit,
    onSearchTypeChanged: (String) -> Unit,
    onDishTypeSelected: (String) -> Unit,
) {
    val timeSuggestions = listOf(5, 20, 45, 60)
    var selectedMealType by remember { mutableStateOf<String?>(null) }
    var switchSearchByMealType by remember { mutableStateOf(false) }
    var selectedTimeFilter by remember { mutableIntStateOf(Int.MAX_VALUE) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (switchSearchByMealType) "Search By Meal Type" else "Search By Ingredients",
                        fontSize = 12.sp,
                    )
                    Switch(
                        checked = switchSearchByMealType,
                        onCheckedChange = { checked ->
                            switchSearchByMealType = checked
                            onSearchTypeChanged(
                                if (checked) "Meal"
                                else "Ingredient"
                            )
                            if (!checked) selectedMealType = null
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                        )
                    )
                }
            }
        }

        item {
            AnimatedVisibility(
                visible = switchSearchByMealType,
                enter = expandVertically(
                    spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntSize.VisibilityThreshold
                    )
                ),
                exit = shrinkVertically()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Meal Type", fontWeight = FontWeight.Bold, fontSize = 14.sp,
                    )

                    mealType.chunked(2).forEach { pair: List<MealType> ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            pair.forEach { meal ->
                                MealTypeItem(
                                    mealType = meal,
                                    isSelected = selectedMealType == meal.type,
                                    onDishTypeSelected = {
                                        if (selectedMealType == it) {
                                            selectedMealType = null
                                        } else {
                                            selectedMealType = it
                                            onDishTypeSelected(it)
                                        }
                                    }
                                )
                            }
                        }
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
            TimeFilterGrid(
                selectedTimeFilter = selectedTimeFilter,
                timeSuggestions = timeSuggestions,
                onTimeFilterSelected = {
                    onTimeFilterSelected(it)
                    selectedTimeFilter = it
                }
            )
        }
    }
}

@Composable
fun RowScope.MealTypeItem(
    mealType: MealType,
    isSelected: Boolean,
    onDishTypeSelected: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .clickable(
                enabled = true,
                onClick = {
                    onDishTypeSelected(mealType.type)
                },
                indication = LocalIndication.current,
                interactionSource = interactionSource
            )
            .weight(1f)
            .height(70.dp)
            .background(Color.LightGray.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        MealTypeSuggestionGridItem(
            imageVector = mealType.imageVector,
            title = mealType.type
        )

        if (isSelected) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .size(16.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .align(Alignment.TopStart),
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Tick",
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(2.dp)
                )
            }
        }
    }
}

@Composable
fun MealTypeSuggestionGridItem(
    imageVector: ImageVector,
    title: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
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

@Composable
fun TimeFilterGrid(
    selectedTimeFilter: Int,
    timeSuggestions: List<Int>,
    onTimeFilterSelected: (Int) -> Unit
) {
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
                    selected = selectedTimeFilter == timeSuggestions[time],
                    leadingIcon = if (selectedTimeFilter == timeSuggestions[time]) {
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

@Preview(showBackground = true)
@Composable
fun PreviewPopularRecipeSuggestionGridItem() {
    MealTypeSuggestionGridItem(
        imageVector = Breakfast_dining,
        title = "Breakfast"
    )
}
