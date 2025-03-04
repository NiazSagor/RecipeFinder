package com.example.recipefinder.ui.recipedetails

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.data.model.RecipeNutrient
import com.example.recipefinder.ui.home.HorizontalList
import com.example.recipefinder.ui.recipedetails.components.PreparationTimeLine
import com.example.recipefinder.ui.recipedetails.components.RecipeIngredientsVerticalListItem
import com.example.recipefinder.ui.recipedetails.components.RecipePreparationBottomSheet
import com.example.recipefinder.ui.recipedetails.components.RecipeServings
import com.example.recipefinder.ui.recipedetails.components.RecipeSummary
import com.example.recipefinder.ui.recipedetails.components.Tip
import com.example.recipefinder.ui.recipedetails.components.TopBar

private const val TAG = "RecipeDetailsScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    paddingValues: PaddingValues,
    recipeDetailViewModel: RecipeDetailsViewModel = hiltViewModel(),
    recipeId: Int,
    onPopCurrent: () -> Unit,
    onTipClick: (Int) -> Unit,
    onTipDetailsClick: (Int) -> Unit,
) {
    var currentRecipeId by remember { mutableIntStateOf(recipeId) }
    val recipeDetails = recipeDetailViewModel.recipeDetail.collectAsStateWithLifecycle()

    LaunchedEffect(currentRecipeId) {
        recipeDetailViewModel.getRecipeDetailsById(currentRecipeId)
    }

    when (recipeDetails.value) {
        is RecipeDetailsState.Error -> {}
        RecipeDetailsState.Loading -> {}
        is RecipeDetailsState.Success -> {
            val listState = rememberLazyListState() // Track scroll position
            var similarRecipes by remember { mutableStateOf<List<Recipe>>(emptyList()) }
            var isSimilarRecipesLoading by remember { mutableStateOf(false) }
            var hasLoaded by remember { mutableStateOf(false) }
            var showMakeTipLayout by remember { mutableStateOf(false) }
            val currentRecipeDetails: Recipe =
                (recipeDetails.value as RecipeDetailsState.Success).recipe
            var openBottomSheet: Boolean by rememberSaveable { mutableStateOf(false) }
            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
            var servings by remember { mutableStateOf(currentRecipeDetails.servings) }
            var isNutritionInfoExpanded by remember { mutableStateOf(false) }
            var nutrients by remember { mutableStateOf<RecipeNutrient?>(null) }
            var isNutritionInfoLoading by remember { mutableStateOf(false) }
            if (isNutritionInfoExpanded) {
                LaunchedEffect(Unit) {
                    isNutritionInfoLoading = true
                    nutrients = recipeDetailViewModel.getNutrients(id = currentRecipeId)
                    isNutritionInfoLoading = false
                }
            }
            Scaffold(
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                topBar = {
                    // recipe title
                    TopBar(
                        isRecipeBookMarked = currentRecipeDetails.isBookmarked,
                        title = currentRecipeDetails.title,
                        onLike = {
                            recipeDetailViewModel.like(
                                recipeId = currentRecipeId,
                                recipe = currentRecipeDetails
                            )
                        },
                        onSave = { recipeDetailViewModel.save(currentRecipeDetails) },
                        onPopCurrent = onPopCurrent, // pop current page
                        scrollBehavior = scrollBehavior,
                    )
                },
                floatingActionButton = {
                    // view cooking instruction
                    ExtendedFloatingActionButton(
                        containerColor = MaterialTheme.colorScheme.primary,
                        onClick = { if (!openBottomSheet) openBottomSheet = true },
                        icon = { Icon(Icons.Filled.PlayArrow, "Start Cooking") },
                        text = {
                            Text(
                                text = "Start Cooking",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        },
                        shape = RoundedCornerShape(32.dp)
                    )
                }
            ) { paddingValues ->
                // open recipe instructions (bottom sheet)
                if (openBottomSheet) {
                    // get recipe instructions
                    recipeDetailViewModel.getAnalyzedRecipeInstructions(id = currentRecipeId)
                    // bottom sheet design
                    RecipePreparationBottomSheet(
                        recipeDetailViewModel = recipeDetailViewModel,
                        modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
                        onDismissRequest = {
                            openBottomSheet = false
                        }
                    )
                }
                LazyColumn(
                    state = listState,
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
                        RecipeSummary(
                            text = currentRecipeDetails.summary.replace(Regex("<.*?>"), ""),
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
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(currentRecipeDetails.image)
                                    .crossfade(true)
                                    .build(),
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
                        PreparationTimeLine(currentRecipeDetails)
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

                    items(currentRecipeDetails.extendedIngredients.size) { index ->
                        RecipeIngredientsVerticalListItem(
                            ingredient = currentRecipeDetails.extendedIngredients[index],
                            currentServings = servings,
                            defaultServing = currentRecipeDetails.servings
                        )
                        if (index < currentRecipeDetails.extendedIngredients.size - 1) {
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
                                if (isNutritionInfoLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(25.dp)
                                    )
                                } else {
                                    Text(
                                        fontWeight = FontWeight.Bold,
                                        text = if (isNutritionInfoExpanded) "Hide Info -" else "View Info +",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.clickable {
                                            isNutritionInfoExpanded = !isNutritionInfoExpanded
                                        }
                                    )
                                }
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
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.size(16.dp))
                    }

                    item {
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.size(16.dp))
                    }

                    item {
                        Tip({
                            showMakeTipLayout = true
                        }, {
                            onTipDetailsClick(recipeId)
                        })
                    }

                    item {
                        Spacer(modifier = Modifier.size(16.dp))
                    }

                    item {
                        val isAtBottom by remember {
                            derivedStateOf {
                                val lastVisibleItemIndex =
                                    listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                                lastVisibleItemIndex != null && lastVisibleItemIndex >= listState.layoutInfo.totalItemsCount - 1
                            }
                        }

                        LaunchedEffect(isAtBottom) {
                            if (isAtBottom && !hasLoaded) {
                                hasLoaded = true
                                isSimilarRecipesLoading = true
                                similarRecipes =
                                    recipeDetailViewModel.getSimilarRecipes(currentRecipeId)
                                isSimilarRecipesLoading = false
                            }
                        }
                        if (isSimilarRecipesLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(25.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        } else {
                            HorizontalList(
                                getLikesForRecipe = {
                                    recipeDetailViewModel.getRecipeLike(it)
                                },
                                onRecipeClick = {
                                    currentRecipeId = it
                                },
                                onSave = { recipeDetailViewModel.save(it) },
                                title = "Similar Recipes",
                                recipes = similarRecipes
                            )
                        }
                    }
                }
            }
            if (showMakeTipLayout) {
                MakeTip(
                    paddingValues = paddingValues,
                    recipeTitle = currentRecipeDetails.title,
                    isVisible = showMakeTipLayout,
                    onSubmit = { tip, uri ->
                        recipeDetailViewModel.sendTip(
                            recipeId = recipeId,
                            tip = tip,
                            photoUri = uri,
                            recipe = currentRecipeDetails
                        )
                        showMakeTipLayout = false
                    },
                    onCancel = { showMakeTipLayout = false },
                )
            }
        }
    }
}


@Composable
fun MakeTip(
    paddingValues: PaddingValues,
    recipeTitle: String,
    isVisible: Boolean,
    onCancel: () -> Unit,
    onSubmit: (String, Uri?) -> Unit
) {
    var tipText by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri // Save the selected image URI
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { it }, // Start at the bottom
                animationSpec = tween(durationMillis = 500)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it }, // Exit towards the bottom
                animationSpec = tween(durationMillis = 500)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Top Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Cancel",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable { onCancel() }
                    )

//                    TextButton(
//                        onClick = { photoPickerLauncher.launch("image/*") }
//                    ) {
//                        Text(text = "Add Photo")
//                    }

                    Text(
                        text = "Submit",
                        color = if (tipText.isNotEmpty()) MaterialTheme.colorScheme.primary else Color.LightGray,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable {
                            if (tipText.isNotEmpty()) onSubmit(tipText, selectedImageUri)
                        }
                    )
                }


                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    text = "Share your tip for $recipeTitle",
                    fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                // TextField covering the rest of the screen
                TextField(
                    value = tipText,
                    onValueChange = { tipText = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    placeholder = { Text("Write your tip here...") },
                )
            }
        }

        if (selectedImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(selectedImageUri),
                contentDescription = "Selected Photo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .align(Alignment.BottomStart)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsScreen() {

}
