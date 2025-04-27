package com.github.fhanko.interview.book_add

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

sealed class BookAddIntent {
    data object InsertBook : BookAddIntent()
    data class InputChanged(val field: InputField, val value: String) : BookAddIntent()
}

data class BookAddState(
    val book: Book = Book()
)

class BookAddViewModel : ViewModel() {
    var state by mutableStateOf(BookAddState())

    fun call(intent: BookAddIntent) {
        viewModelScope.launch {
            when (intent) {
                BookAddIntent.InsertBook -> insertBook()
                is BookAddIntent.InputChanged -> inputChanged(intent.field, intent.value)
            }
        }
    }

    private fun inputChanged(field: InputField, value: String) {
        state = state.copy(book =
            when (field) {
                InputField.Title -> state.book.copy(title = value)
                InputField.Author -> state.book.copy(author = value)
                InputField.Notes -> state.book.copy(notes = value)
                InputField.ISBN -> state.book.copy(isbn = value)
            }
        )
    }

    private suspend fun insertBook() {
        withContext (Dispatchers.IO) {
            AppDatabase.instance.bookDao().insert(state.book)
        }
    }
}