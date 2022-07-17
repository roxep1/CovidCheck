package com.bashkir.covidcheck.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.bashkir.covidcheck.ui.theme.normalText
import com.bashkir.covidcheck.ui.theme.placeHolderText
import com.bashkir.covidcheck.ui.theme.smallPadding

@Composable
fun SearchTextField(
    searchTextState: MutableState<TextFieldValue>,
    modifier: Modifier = Modifier
) =
    OutlinedTextField(
        modifier = modifier
            .padding(horizontal = smallPadding)
            .fillMaxWidth(),
        value = searchTextState.value,
        onValueChange = {
            searchTextState.value = it
        },
        placeholder = {
            Text(
                "Поиск",
                style = placeHolderText
            )
        },
        trailingIcon = {
            if (searchTextState.value.text.isNotEmpty() && searchTextState.value.text.isNotBlank()) {
                IconButton(
                    onClick = {
                        searchTextState.value =
                            TextFieldValue("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close icon"
                    )
                }
            }
        },
        textStyle = TextStyle(fontSize = normalText),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search icon",
                tint = Color.Gray
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            if (isSystemInDarkTheme()) Color.White else Color.Black,
            backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White
        )
    )