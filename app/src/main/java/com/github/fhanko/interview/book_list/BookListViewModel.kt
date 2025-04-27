package com.github.fhanko.interview.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.fhanko.interview.AppDatabase
import com.github.fhanko.interview.Book
import com.github.fhanko.interview.ReadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class BookListIntent {
    data object LoadBooks : BookListIntent()
    data class ToggleReadState(val bookId: Int) : BookListIntent()
}

data class BookListState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = true
)

class BookListViewModel : ViewModel() {
    private val _state = MutableStateFlow(BookListState())
    val state: StateFlow<BookListState> = _state

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
        _state.update { it.copy(books = books, isLoading = false) }
    }

    private suspend fun toggleReadState(bookId: Int) {
        _state.update {
            it.copy(books = it.books.map { b ->
                if (b.id == bookId)
                    b.copy(readState = ReadState.entries[(b.readState.ordinal + 1) % ReadState.entries.size])
                else
                    b
            })
        }
        withContext (Dispatchers.IO) {
            state.value.books.find { it.id == bookId }?.let { AppDatabase.instance.bookDao().update(it) }
        }
    }
}