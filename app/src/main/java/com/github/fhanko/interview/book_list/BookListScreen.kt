package com.github.fhanko.interview.book_list

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.fhanko.interview.Book
import com.github.fhanko.interview.R
import com.github.fhanko.interview.ReadState
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(viewModel: BookListViewModel, navigation: NavController) {
    viewModel.call(BookListIntent.LoadBooks)
    val books = viewModel.state.books

    val listState = rememberLazyListState()
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Book List") }) },
        floatingActionButton = { AddBookButton() { navigation.navigate("add") } }
    ) { innerPadding ->
        when {
            viewModel.state.isLoading ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            books.isEmpty() ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "You have no books, please add a book.")
                }
            else ->
                LazyColumn(
                    state = listState,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    items(books.size) { index ->
                        BookCard(book = books[index], onBookDetails = {
                            navigation.navigate("details/$it")
                        }) {
                            viewModel.call(BookListIntent.ToggleReadState(books[index].id!!))
                        }
                    }
                }
        }
    }
}

@Composable
fun AddBookButton(onAddBookClick: () -> Unit) {
    ExtendedFloatingActionButton(onClick = {
        onAddBookClick()
    }) {
        Row {
            Icon(Icons.Default.Add, "")
            Icon(Icons.Default.Book, "Add book button")
        }
    }
}

@Composable
fun BookCard(book: Book, onBookDetails: (Int) -> Unit, onToggleReadState: () -> Unit) {
    Card(modifier = Modifier.padding(4.dp).clickable { onBookDetails(book.id!!) }) {
        val coverSize = 100.dp
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
            Column(modifier = Modifier
                .padding(6.dp)
                .weight(0.8f)) {
                Text(text = book.title, style = MaterialTheme.typography.titleLarge)
                Row {
                    Text(text = book.author, style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = book.isbn ?: "",
                        style = MaterialTheme.typography.labelSmall.merge(color = Color.Gray),
                        modifier = Modifier.align(Alignment.Bottom)
                    )
                }
            }
            Column(modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(0.3f)) {
                ToggleReadButton(readState = book.readState) { onToggleReadState() }
            }
        }
    }
}

data class ReadIcon(val icon: Int, val text: String)
val readMap = mapOf(
    ReadState.Unread to ReadIcon(R.drawable.book, "Not read"),
    ReadState.Partial to ReadIcon(R.drawable.book_half, "Reading"),
    ReadState.Read to ReadIcon(R.drawable.book_fill, "Read")
)
@Composable
fun ToggleReadButton(readState: ReadState, onToggle: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = readMap[readState]?.icon ?: R.drawable.book),
            "Read status: ${readMap[readState]?.text}",
            modifier = Modifier
                .size(90.dp, 44.dp)
                .align(Alignment.CenterHorizontally)
                .clickable { onToggle() }
        )
        Text(
            text = readMap[readState]?.text ?: "",
            style = MaterialTheme.typography.labelSmall.merge(color = Color.Gray),
            textAlign = TextAlign.Center
        )
    }
}