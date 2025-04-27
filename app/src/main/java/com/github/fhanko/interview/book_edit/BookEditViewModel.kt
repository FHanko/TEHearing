package com.github.fhanko.interview.book_edit

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

enum class InputField { Title, Author, Notes, ISBN }

sealed class BookEditIntent {
    data class InputChanged(val field: InputField, val value: String) : BookEditIntent()
}

data class BookEditState(
    val book: Book = Book(),
    val isLoading: Boolean = true
)

open class BookEditViewModel : ViewModel() {
    var state by mutableStateOf(BookEditState())
        protected set

    fun call(intent: BookEditIntent) {
        viewModelScope.launch {
            when (intent) {
                is BookEditIntent.InputChanged -> inputChanged(intent.field, intent.value)
            }
        }
    }

    private suspend fun inputChanged(field: InputField, value: String) {
        state = state.copy(book =
            when (field) {
                InputField.Title -> state.book.copy(title = value)
                InputField.Author -> state.book.copy(author = value)
                InputField.Notes -> state.book.copy(notes = value)
                InputField.ISBN -> state.book.copy(isbn = value)
            }
        )
        if (state.book.id != null) updateBook()
    }

    private suspend fun updateBook() {
        withContext (Dispatchers.IO) {
            AppDatabase.instance.bookDao().update(state.book)
        }
    }
}