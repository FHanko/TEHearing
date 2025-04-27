package com.github.fhanko.interview.book_edit.add

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.fhanko.interview.book_edit.BookEditColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAddScreen(viewModel: BookAddViewModel, navigation: NavController) {
    val state by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    ConfirmDialog(show = showDialog) {
        viewModel.call(BookAddIntent.InsertBook)
        navigation.popBackStack()
    }
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Add Book") }) }
    ) { innerPadding ->
        BookEditColumn(state.book, viewModel, Modifier.padding(innerPadding)) {
            SaveBookButton(state.book.title != "" && state.book.author != "")
                { showDialog = true }
        }
    }
}

@Composable
fun ConfirmDialog(show: Boolean, confirmAction: () -> Unit) {
    if (show) {
        AlertDialog(
            title = { Text("Book successfully added") },
            onDismissRequest = { }, confirmButton = {
                Button(onClick = { confirmAction() }) {
                    Text(text = "Done")
                }
            })
    }
}

@Composable
fun SaveBookButton(enabled: Boolean, onClick: () -> Unit) {
    Row {
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp), shape = RectangleShape,
            enabled = enabled,
            onClick = onClick) {
            Text(text = "Continue", style = MaterialTheme.typography.bodyLarge)
        }
    }
}