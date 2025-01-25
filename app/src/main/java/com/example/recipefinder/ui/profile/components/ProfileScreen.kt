package com.example.recipefinder.ui.profile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.recipefinder.R
import com.example.recipefinder.ui.home.components.BottomNavigationBar
import com.example.recipefinder.ui.home.elements.RecipeHorizontalListItem

// TODO: fix the bottom nav
@Composable
fun ProfileScreen(
    viewmodel: ProfileScreenViewModel = hiltViewModel(),
    onRecipeClick: (Int) -> Unit,
    onBottomBarClick: (String) -> Unit,
) {
    val bookmarkedRecipes = viewmodel.profileState.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = { BottomNavigationBar(onBottomBarClick) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AsyncImage(
                        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                        model = "",
                        contentDescription = "",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = "Niaz",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


                Text(
                    text = "Saved Recipes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                when (bookmarkedRecipes.value) {
                    is ProfileState.Error -> {}
                    ProfileState.Idle -> {}
                    ProfileState.Loading -> {}
                    is ProfileState.Success -> {
                        val recipes =
                            (bookmarkedRecipes.value as ProfileState.Success).bookmarkedRecipes
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            content = {
                                itemsIndexed(recipes) { index, recipe ->
                                    RecipeHorizontalListItem(
                                        getLikesForRecipe = { viewmodel.getRecipeLike(it) },
                                        recipe = recipe,
                                        searchItem = true,
                                        onRecipeClick = { onRecipeClick(it) },
                                        onSave = { viewmodel.saveRecipe(it) }
                                    )
                                }
                            },
                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewProfileScreen() {

}
