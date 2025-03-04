package com.example.recipefinder.ui.recipedetails.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipefinder.data.model.RecipeAnalyzedInstructions
import com.example.recipefinder.data.model.Step
import com.example.recipefinder.ui.recipedetails.RecipeDetailsViewModel
import com.example.recipefinder.ui.recipedetails.RecipeInstructionsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipePreparationBottomSheet(
    recipeDetailViewModel: RecipeDetailsViewModel,
    modifier: Modifier,
    onDismissRequest: () -> Unit
) {
    val recipeInstructions = recipeDetailViewModel.recipeInstructions.collectAsStateWithLifecycle()
    when (recipeInstructions.value) {
        is RecipeInstructionsState.Error -> {}
        RecipeInstructionsState.Loading -> {

        }

        is RecipeInstructionsState.Success -> {
            val recipeInstructions: RecipeAnalyzedInstructions =
                (recipeInstructions.value as RecipeInstructionsState.Success).recipe
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
            ModalBottomSheet(
                modifier = modifier,
                sheetState = sheetState,
                onDismissRequest = {
                    onDismissRequest()
                },
                content = {
                    BottomSheetContentLayout(recipeInstructions)
                }
            )
        }
    }
}

@Composable
fun BottomSheetContentLayout(recipeInstructions: RecipeAnalyzedInstructions) {
    val pagerState = rememberPagerState(pageCount = { recipeInstructions.steps.size })
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "${pagerState.currentPage + 1} of ${recipeInstructions.steps.size}",
            fontWeight = FontWeight.Bold
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page: Int ->
            val step: Step = recipeInstructions.steps[page]
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = step.step,
                    fontSize = 22.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomSheetContentLayout() {

}

@Preview(showBackground = true)
@Composable
fun PreviewRecipePreparationBottomSheet() {

}
