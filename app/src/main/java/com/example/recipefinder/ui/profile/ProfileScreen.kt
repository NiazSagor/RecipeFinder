package com.example.recipefinder.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
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
import com.example.recipefinder.ui.myiconpack.MyIconPack
import com.example.recipefinder.ui.myiconpack.Wave
import com.example.recipefinder.ui.profile.activity.ActivityScreen
import com.example.recipefinder.ui.profile.savedrecipe.SavedRecipeScreen


@Composable
fun ProfileScreen(
    parentPaddingValues: PaddingValues,
    viewmodel: ProfileScreenViewModel = hiltViewModel(),
    onRecipeClick: (Int) -> Unit,
) {
    val profileState by viewmodel.profileState.collectAsStateWithLifecycle()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Saved Recipe", "Activity")

    Scaffold(
        modifier = Modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                )
                .fillMaxSize()
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
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Column(
                    modifier = Modifier
                        .padding(top = paddingValues.calculateTopPadding() + parentPaddingValues.calculateTopPadding())
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AsyncImage(
                        placeholder = painterResource(id = R.drawable.ic_launcher_background),
                        model = viewmodel.getUserProfilePhoto(),
                        contentDescription = "User profile image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = viewmodel.getUserName(),
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

                when (profileState) {
                    is ProfileState.Error -> {}
                    ProfileState.Idle -> {}
                    ProfileState.Loading -> {}
                    is ProfileState.Success -> {
                        val recipes =
                            (profileState as ProfileState.Success).data.bookmarkedRecipes

                        val myRatings = (profileState as ProfileState.Success).data.myRatings

                        val myTips = (profileState as ProfileState.Success).data.myTips

                        when (selectedTabIndex) {
                            0 -> {
                                SavedRecipeScreen(
                                    paddingValues = parentPaddingValues,
                                    recipes = recipes,
                                    viewmodel = viewmodel,
                                    onRecipeClick = onRecipeClick
                                )
                            }

                            1 -> {
                                ActivityScreen(
                                    paddingValues = parentPaddingValues,
                                    myRatings = myRatings,
                                    myTips = myTips,
                                    getLikesForRecipe = { viewmodel.getRecipeLike(it) },
                                    onRecipeClick = onRecipeClick,
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

@Preview
@Composable
fun PreviewProfileScreen() {

}
