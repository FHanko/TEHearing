package com.github.fhanko.interview.book_add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAddScreen(viewModel: BookAddViewModel, navigation: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Book List") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row {
                TextField(value = "", onValueChange = {}, placeholder = { Text(text = "Title") })
            }
            Row {
                TextField(value = "", onValueChange = {})
            }
            Row {
                TextField(value = "", onValueChange = {})
            }
            Row {
                Button(onClick = { /*TODO*/ }) {
                    
                }
            }
        }
    }
}
