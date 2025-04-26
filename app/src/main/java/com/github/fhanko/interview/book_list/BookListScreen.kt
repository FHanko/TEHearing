package com.github.fhanko.interview.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.fhanko.interview.Book
import kotlin.math.sqrt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(viewModel: BookListViewModel, navigation: NavController) {
    viewModel.call(BookListIntent.LoadBooks)
    val books = viewModel.state.books

    val listState = rememberLazyListState()
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "BookApp") }) },
        floatingActionButton = { AddBookButton() }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(innerPadding)
        ) {
            items(books.size) { bookId ->
                BookCard(book = books[bookId])
            }
        }
    }
}

@Composable
@Preview
fun AddBookButton() {
    ExtendedFloatingActionButton(onClick = {

    }) {
        Row {
            Icon(Icons.Default.Add, "")
            Icon(Icons.Default.Book, "Add book button")
        }
    }
}

@Composable
fun BookCard(book: Book) {
    Card(modifier = Modifier.padding(4.dp)) {
        val coverSize = 110.dp
        Row(
            Modifier
                .height(coverSize)
                .fillMaxWidth()) {
            Column {
                Box(
                    modifier = Modifier
                        .size(height = coverSize, width = coverSize * (1 / sqrt(2.0f)))
                        .background(Color.Gray)
                )
            }
            Column(modifier = Modifier.padding(start = 6.dp)) {
                Text(text = book.title, style = MaterialTheme.typography.titleLarge)
                Text(text = book.author, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = book.isbn ?: "",
                        style = MaterialTheme.typography.labelSmall.merge(color = Color.Gray)
                    )
                }
            }
        }
    }
}