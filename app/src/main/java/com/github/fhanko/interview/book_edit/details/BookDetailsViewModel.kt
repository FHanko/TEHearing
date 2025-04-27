package com.github.fhanko.interview.book_edit.details

import androidx.lifecycle.viewModelScope
import com.github.fhanko.interview.AppDatabase
import com.github.fhanko.interview.book_edit.BookEditViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class BookDetailsIntent {
    data object BookInit: BookDetailsIntent()
}

class BookDetailsViewModel(private val bookId: Int): BookEditViewModel() {
    fun call(intent: BookDetailsIntent) {
        viewModelScope.launch {
            when (intent) {
                BookDetailsIntent.BookInit -> initBook()
            }
        }
    }

    private suspend fun initBook() {
        _state.update {
            it.copy(book = withContext (Dispatchers.IO) {
                AppDatabase.instance.bookDao().getById(bookId)
            }, isLoading = false)
        }
    }
}