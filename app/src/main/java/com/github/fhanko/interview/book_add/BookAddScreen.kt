package com.github.fhanko.interview.book_add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAddScreen(viewModel: BookAddViewModel, navigation: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Add Book") }) }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())) {
            BookInput(value = viewModel.state.book.title, placeholder = "Title*") {
                viewModel.call(BookAddIntent.InputChanged(InputField.Title, it))
            }
            BookInput(value = viewModel.state.book.author, placeholder = "Author*") {
                viewModel.call(BookAddIntent.InputChanged(InputField.Author, it))
            }
            BookInput(value = viewModel.state.book.isbn, placeholder = "ISBN") {
                viewModel.call(BookAddIntent.InputChanged(InputField.ISBN, it))
            }
            BookInput(value = viewModel.state.book.notes, placeholder = "Notes") {
                viewModel.call(BookAddIntent.InputChanged(InputField.Notes, it))
            }
            Row {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp), shape = RectangleShape,
                    onClick = {
                        viewModel.call(BookAddIntent.InsertBook)
                        navigation.navigate("home")
                    }) {
                    Text(text = "Add Book", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
fun BookInput(value: String?, placeholder: String, onChange: (String) -> Unit) {
    Row {
        TextField(
            value = value ?: "",
            label = { Text(text = placeholder) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            onValueChange = {
                onChange(it)
            })
    }
}