package com.github.fhanko.interview.book_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.fhanko.interview.AppDatabase
import com.github.fhanko.interview.Book
import com.github.fhanko.interview.ReadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Level
import java.util.logging.Logger

sealed class BookListIntent {
    data object LoadBooks : BookListIntent()
    data class ToggleReadState(val bookId: Int) : BookListIntent()
}

data class BookListState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = true
)

class BookListViewModel : ViewModel() {
    var state by mutableStateOf(BookListState())

    fun call(intent: BookListIntent) {
        viewModelScope.launch {
            when (intent) {
                BookListIntent.LoadBooks -> loadBooks()
                is BookListIntent.ToggleReadState -> toggleReadState(intent.bookId)
            }
        }
    }

    private suspend fun loadBooks() {
        val books = withContext (Dispatchers.IO) {
            AppDatabase.instance.bookDao().getAll()
        }
        state = state.copy(books = books, isLoading = false)
    }

    private suspend fun toggleReadState(bookId: Int) {
        state = state.copy(books = state.books.map {
            if (it.id == bookId)
                it.copy(readState = ReadState.entries[(it.readState.ordinal + 1) % ReadState.entries.size])
            else
                it
        })
        withContext (Dispatchers.IO) {
            state.books.find { it.id == bookId }?.let { AppDatabase.instance.bookDao().update(it) }
        }
    }
}