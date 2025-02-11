package com.example.recipefinder.ui.post

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostRecipeScreen(
    paddingValues: PaddingValues,
    viewmodel: PostRecipeViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val postRecipeState = viewmodel.postState.collectAsStateWithLifecycle()
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var addPhotoEnabled by remember { mutableStateOf<Boolean>(true) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Share your recipe") },
                navigationIcon = {
                    IconButton(
                        onClick = { onBackClick() },
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            when (postRecipeState.value) {
                is PostRecipeScreenState.Error -> {
                    Toast.makeText(
                        /* context = */ context,
                        /* text = */ (postRecipeState.value as PostRecipeScreenState.Error).message,
                        /* duration = */ Toast.LENGTH_SHORT
                    ).show()
                }

                PostRecipeScreenState.Idle -> {
                    Column {
                        OutlinedTextField(
                            value = title.value,
                            onValueChange = { title.value = it },
                            label = { Text("Recipe Title") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = description.value,
                            onValueChange = { description.value = it },
                            label = { Text("Recipe Description") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        if (addPhotoEnabled) {
                            OutlinedButton(
                                onClick = { photoPickerLauncher.launch("image/*") },
                            ) {
                                Text("Add Photo")
                            }
                        }

                        selectedImageUri?.let {
                            addPhotoEnabled = false
                            Spacer(modifier = Modifier.height(16.dp))
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(selectedImageUri),
                                    contentDescription = "Selected Photo",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                )
                                IconButton(
                                    onClick = {
                                        addPhotoEnabled = true
                                        selectedImageUri = null
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(8.dp)
                                ) {
                                    Icon(
                                        tint = MaterialTheme.colorScheme.primary,
                                        imageVector = Icons.Default.Cancel,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    Button(
                        onClick = {
                            if (selectedImageUri != null) {
                                viewmodel.postRecipe(
                                    post = description.value,
                                    recipeTitle = title.value,
                                    recipeImageUri = selectedImageUri!!
                                )
                            } else {
                                Toast.makeText(
                                    /* context = */ context,
                                    /* text = */ "Please add a recipe photo before posting",
                                    /* duration = */ Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RectangleShape,
                        enabled = title.value.isNotEmpty() && description.value.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("POST RECIPE")
                    }
                }

                PostRecipeScreenState.Posting -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                PostRecipeScreenState.PostSuccess -> {
                    Toast.makeText(
                        /* context = */ context,
                        /* text = */ "Recipe posted successfully",
                        /* duration = */ Toast.LENGTH_SHORT
                    )
                    title.value = ""
                    description.value = ""
                    selectedImageUri = null
                    addPhotoEnabled = true
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewPostRecipeScreen() {
    PostRecipeScreen(
        paddingValues = PaddingValues(0.dp)
    ) {}
}
