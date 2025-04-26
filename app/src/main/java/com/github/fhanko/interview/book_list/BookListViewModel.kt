package com.github.fhanko.interview.book_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.fhanko.interview.AppDatabase
import com.github.fhanko.interview.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class BookListIntent {
    data object LoadBooks : BookListIntent()
}

data class BookListState(
    val books: List<Book> = emptyList()
)

class BookListViewModel : ViewModel() {

    var state by mutableStateOf(BookListState())

    fun call(intent: BookListIntent) {
        viewModelScope.launch {
            when (intent) {
                BookListIntent.LoadBooks -> loadBooks()
            }
        }
    }

    private suspend fun loadBooks() {
        val books = withContext (Dispatchers.IO) {
            AppDatabase.instance.bookDao().getAll()
        }
        state = state.copy(books = books)
    }
}