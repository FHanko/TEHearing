package com.github.fhanko.interview.book_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.fhanko.interview.AppDatabase
import com.github.fhanko.interview.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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
    protected val _state = MutableStateFlow(BookEditState())
    val state: StateFlow<BookEditState> = _state

    fun call(intent: BookEditIntent) {
        viewModelScope.launch {
            when (intent) {
                is BookEditIntent.InputChanged -> inputChanged(intent.field, intent.value)
            }
        }
    }

    private suspend fun inputChanged(field: InputField, value: String) {
        _state.update {
            it.copy(book =
                when (field) {
                InputField.Title -> it.book.copy(title = value)
                InputField.Author -> it.book.copy(author = value)
                InputField.Notes -> it.book.copy(notes = value)
                InputField.ISBN -> it.book.copy(isbn = value)
            })
        }
        if (state.value.book.id != null) updateBook()
    }

    protected suspend fun updateBook() {
        withContext (Dispatchers.IO) {
            AppDatabase.instance.bookDao().update(state.value.book)
        }
    }
}