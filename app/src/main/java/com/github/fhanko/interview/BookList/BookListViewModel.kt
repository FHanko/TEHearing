package com.github.fhanko.interview.BookList

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
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

class BookListViewModel(context: Context) : ViewModel() {
    private val db: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "app_data"
    ).build()

    var state by mutableStateOf(BookListState())

    fun onAction(intent: BookListIntent) {
        viewModelScope.launch {
            when (intent) {
                BookListIntent.LoadBooks -> loadBooks()
            }
        }
    }

    private suspend fun loadBooks() {
        val books = withContext (Dispatchers.IO) {
            db.bookDao().getAll()
        }
        state = state.copy(books = books)
    }

    private suspend fun insert(book: Book) {
        withContext (Dispatchers.IO) { db.bookDao().insert(book) }
    }
}