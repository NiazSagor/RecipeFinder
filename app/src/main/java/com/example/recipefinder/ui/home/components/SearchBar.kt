package com.example.recipefinder.ui.home.components;

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    query: TextFieldValue,
    hint: String,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    onBack: () -> Unit,
    searching: Boolean,
    focused: Boolean,
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedVisibility(visible = focused) {
            // Back button
            IconButton(
                modifier = Modifier.padding(start = 2.dp),
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onBack()
                }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = androidx.compose.material3.MaterialTheme.colorScheme.primary
                )
            }
        }

        SearchTextField(
            query,
            hint,
            onQueryChange,
            onSearchFocusChange,
            onClearQuery,
            searching,
            focused,
            modifier.weight(1f)
        )
    }
}

/**
 * This is a stateless TextField for searching with a Hint when query is empty,
 * and clear and loading [IconButton]s to clear query or show progress indicator when
 * a query is in progress.
 */
@Composable
fun SearchTextField(
    query: TextFieldValue,
    hint: String,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    searching: Boolean,
    focused: Boolean,
    modifier: Modifier = Modifier
) {

    val focusRequester = remember { FocusRequester() }

    Surface(
        modifier = modifier
            .then(
                Modifier
                    .height(56.dp)
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        start = if (!focused) 16.dp else 0.dp,
                        end = 16.dp
                    )
            ),
        color = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(percent = 50),
        border = BorderStroke(1.dp, MaterialTheme.colors.onBackground)
    ) {

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = modifier
            ) {

                if (query.text.isEmpty()) {
                    SearchHint(hint, modifier.padding(start = 24.dp, end = 8.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    BasicTextField(
                        value = query,
                        cursorBrush = SolidColor(MaterialTheme.colors.onBackground),
                        textStyle = TextStyle(color = MaterialTheme.colors.onBackground),
                        onValueChange = onQueryChange,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .onFocusChanged {
                                onSearchFocusChange(it.isFocused)
                            }
                            .focusRequester(focusRequester)
                            .padding(top = 9.dp, bottom = 8.dp, start = 24.dp, end = 8.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        )
                    )

                    when {
                        searching && query.text.isNotEmpty() -> {
                            CircularProgressIndicator(
                                color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .size(30.dp)
                            )
                        }

                        query.text.isNotEmpty() -> {
                            IconButton(onClick = onClearQuery) {
                                Icon(
                                    imageVector = Icons.Filled.Cancel,
                                    contentDescription = null,
                                    tint = androidx.compose.material3.MaterialTheme.colorScheme.primary
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
private fun SearchHint(hint: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)

    ) {
        Text(
            color = MaterialTheme.colors.onSurface,
            text = hint,
        )
    }
}
