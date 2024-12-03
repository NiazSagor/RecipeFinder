package com.example.recipefinder.ui.recipedetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.model.RecipeNutrient
import com.example.recipefinder.ui.home.HorizontalList
import com.example.recipefinder.ui.recipedetails.components.ExpandableText
import com.example.recipefinder.ui.recipedetails.components.PreparationTimeLine
import com.example.recipefinder.ui.recipedetails.components.RecipeIngredientsVerticalListItem
import com.example.recipefinder.ui.recipedetails.components.RecipePreparationBottomSheet
import com.example.recipefinder.ui.recipedetails.components.RecipeServings
import com.example.recipefinder.ui.recipedetails.components.TopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    recipeDetailViewModel: RecipeDetailsViewModel = hiltViewModel(),
    recipeId: Int,
    onPopCurrent: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val recipeDetails = recipeDetailViewModel.recipeDetail.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        recipeDetailViewModel.getRecipeDetailsById(recipeId)
    }

    when (recipeDetails.value) {
        is RecipeDetailsState.Error -> {}
        RecipeDetailsState.Loading -> {}
        is RecipeDetailsState.Success -> {
            val recipeDetails = (recipeDetails.value as RecipeDetailsState.Success).recipe
            var openBottomSheet by rememberSaveable { mutableStateOf(false) }
            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
            var servings by remember { mutableStateOf(recipeDetails.servings) }
            var isNutritionInfoExpanded by remember { mutableStateOf(false) }
            var nutrients by remember { mutableStateOf<RecipeNutrient?>(null) }
            if (isNutritionInfoExpanded) {
                LaunchedEffect(Unit) {
                    nutrients = recipeDetailViewModel.getNutrients(id = recipeId)
                }
            }
            Scaffold(
                topBar = { TopBar(onPopCurrent, recipeDetails.title, scrollBehavior) },
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
                    recipeDetailViewModel.getAnalyzedRecipeInstructions(id = recipeId)
                    RecipePreparationBottomSheet(
                        recipeDetailViewModel = recipeDetailViewModel,
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
                        bottom = 50.dp
                    )
                ) {
                    item {
                        ExpandableText(
                            text = recipeDetails.summary.replace(Regex("<.*?>"), ""),
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.size(16.dp))
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                                .background(Color.White)
                        ) {
                            AsyncImage(
                                model = recipeDetails.image,
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
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
                        PreparationTimeLine(recipeDetails)
                    }
                    item {
                        Spacer(modifier = Modifier.size(16.dp))
                    }
                    item {
                        RecipeServings(servings) {
                            servings = it
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.size(16.dp))
                    }

                    items(recipeDetails.extendedIngredients.size) { index ->
                        RecipeIngredientsVerticalListItem(
                            ingredient = recipeDetails.extendedIngredients[index],
                            currentServings = servings,
                            defaultServing = recipeDetails.servings
                        )
                        if (index < recipeDetails.extendedIngredients.size - 1) {
                            Divider(
                                color = Color.Gray,
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.size(16.dp))
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Nutrition Info",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = if (isNutritionInfoExpanded) "Hide Info -" else "View Info +",
                                    fontSize = 12.sp,
                                    color = Color.Cyan,
                                    modifier = Modifier.clickable {
                                        isNutritionInfoExpanded = !isNutritionInfoExpanded
                                    }
                                )
                            }
                        }
                    }

                    if (isNutritionInfoExpanded && nutrients != null) {
                        item {
                            Spacer(modifier = Modifier.size(16.dp))
                        }
                        items(4) { index ->
                            val nutrientName =
                                when (index) {
                                    0 -> "calories"
                                    1 -> "carbohydrates"
                                    2 -> "fat"
                                    3 -> "protein"
                                    else -> ""
                                }
                            val nutrientAmount =
                                when (index) {
                                    0 -> nutrients?.calorieCount
                                    1 -> nutrients?.carbohydrates
                                    2 -> nutrients?.fats
                                    3 -> nutrients?.proteins
                                    else -> ""
                                }
                            AnimatedVisibility(
                                visible = isNutritionInfoExpanded, // Condition to animate
                                enter = fadeIn() + expandVertically(), // Entry animation
                                exit = fadeOut() + shrinkVertically()  // Exit animation
                            ) {
                                Column() {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = nutrientName,
                                        )
                                        if (nutrientAmount != null) {
                                            Text(
                                                text = nutrientAmount,
                                                color = Color.Black,
                                            )
                                        }
                                    }
                                    if (index < 4 - 1) {
                                        Divider(
                                            color = Color.Gray,
                                            thickness = 1.dp,
                                            modifier = Modifier.padding(vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.size(16.dp))
                        }
                        item {
                            Text(
                                text = "Estimated values based on one serving size.",
                                fontSize = 10.sp,
                                color = Color.LightGray
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.size(16.dp))
                    }

                    item {
                        var similarRecipes by remember { mutableStateOf<List<Recipe>>(emptyList()) }
                        LaunchedEffect(Unit) {
                            similarRecipes = recipeDetailViewModel.getSimilarRecipes(recipeId)
                        }
                        HorizontalList(
                            onRecipeClick = {

                            },
                            title = "Similar Recipes",
                            recipes = similarRecipes
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsScreen() {

}
