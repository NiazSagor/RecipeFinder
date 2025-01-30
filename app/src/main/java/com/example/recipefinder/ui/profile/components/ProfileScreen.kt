package com.example.recipefinder.ui.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import com.example.recipefinder.data.model.Recipe
import com.example.recipefinder.ui.home.HorizontalList
import com.example.recipefinder.ui.home.components.BottomNavigationBar
import com.example.recipefinder.ui.home.elements.RecipeHorizontalListItem
import com.example.recipefinder.ui.myiconpack.MyIconPack
import com.example.recipefinder.ui.myiconpack.Wave

// TODO: fix the bottom nav
@Composable
fun ProfileScreen(
    viewmodel: ProfileScreenViewModel = hiltViewModel(),
    onRecipeClick: (Int) -> Unit,
    onBottomBarClick: (String) -> Unit,
) {
    val bookmarkedRecipes = viewmodel.profileState.collectAsStateWithLifecycle()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Saved Recipe", "Activity")

    Scaffold(
        bottomBar = { BottomNavigationBar(onBottomBarClick) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                )
        ) {

            Image(
                imageVector = MyIconPack.Wave,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .graphicsLayer(scaleY = -1f),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
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
                        placeholder = painterResource(id = R.drawable.ic_launcher_background),
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

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    backgroundColor = Color.White,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title, color = MaterialTheme.colorScheme.primary) },
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index }
                        )
                    }
                }

                when (bookmarkedRecipes.value) {
                    is ProfileState.Error -> {}
                    ProfileState.Idle -> {}
                    ProfileState.Loading -> {}
                    is ProfileState.Success -> {
                        val recipes =
                            (bookmarkedRecipes.value as ProfileState.Success).bookmarkedRecipes

                        when (selectedTabIndex) {
                            0 -> {
                                SavedRecipeScreen(
                                    recipes, viewmodel
                                ) {
                                    onRecipeClick(it)
                                }
                            }

                            1 -> {
                                ActivityScreen(
                                    myRatings = emptyList(),
                                    myTips = emptyList(),
                                    getLikesForRecipe = { viewmodel.getRecipeLike(it) },
                                    onRecipeClick = { onRecipeClick(it) },
                                    onSave = { viewmodel.saveRecipe(it) }
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun SavedRecipeScreen(
    recipes: List<Recipe>,
    viewmodel: ProfileScreenViewModel,
    onRecipeClick: (Int) -> Unit,
) {
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

@Composable
fun ActivityScreen(
    myRatings: List<Recipe>,
    myTips: List<Recipe>,
    getLikesForRecipe: suspend (Int) -> Int,
    onRecipeClick: (Int) -> Unit,
    onSave: (Recipe) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalList(
                    getLikesForRecipe = { getLikesForRecipe(it) },
                    onRecipeClick = { },
                    onSave = { },
                    title = "My Ratings (${myRatings.size})",
                    recipes = myRatings
                )
                if (myRatings.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth().size(100.dp),
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
                onRecipeClick = { },
                onSave = { },
                title = "My Tips (${myTips.size})",
                recipes = myTips
            )
            if (myTips.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth().size(100.dp),
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

@Preview
@Composable
fun PreviewProfileScreen() {

}
