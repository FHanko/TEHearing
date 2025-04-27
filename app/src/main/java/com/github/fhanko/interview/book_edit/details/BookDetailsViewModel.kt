package com.github.fhanko.interview.book_edit.details

import androidx.lifecycle.viewModelScope
import com.github.fhanko.interview.AppDatabase
import com.github.fhanko.interview.Book
import com.github.fhanko.interview.ReadState
import com.github.fhanko.interview.book_edit.BookEditViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class BookDetailsIntent {
    data object BookInit: BookDetailsIntent()
    data object RemoveBook: BookDetailsIntent()
    data object ToggleReadState: BookDetailsIntent()
}

class BookDetailsViewModel(private val bookId: Int): BookEditViewModel() {
    fun call(intent: BookDetailsIntent) {
        viewModelScope.launch {
            when (intent) {
                BookDetailsIntent.BookInit -> initBook()
                BookDetailsIntent.ToggleReadState -> toggleReadState()
                BookDetailsIntent.RemoveBook -> removeBook()
            }
        }
    }

    private suspend fun initBook() {
        _state.update {
            it.copy(book = withContext (Dispatchers.IO) {
                AppDatabase.instance.bookDao().getById(bookId) ?: Book()
            }, isLoading = false)
        }
    }

    private suspend fun toggleReadState() {
        _state.update {
            it.copy(book = it.book.copy(
                readState = ReadState.entries[(it.book.readState.ordinal + 1) % ReadState.entries.size]
            ))
        }
        updateBook()
    }

    private suspend fun removeBook() {
        withContext (Dispatchers.IO) {
            AppDatabase.instance.bookDao().delete(_state.value.book)
        }
    }
}