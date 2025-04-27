package com.github.fhanko.interview.book_edit.add

import androidx.lifecycle.viewModelScope
import com.github.fhanko.interview.AppDatabase
import com.github.fhanko.interview.book_edit.BookEditViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


sealed class BookAddIntent {
    data object InsertBook : BookAddIntent()
}

class BookAddViewModel: BookEditViewModel() {
    fun call(intent: BookAddIntent) {
        viewModelScope.launch {
            when (intent) {
                BookAddIntent.InsertBook -> insertBook()
            }
        }
    }

    private suspend fun insertBook() {
        withContext (Dispatchers.IO) {
            AppDatabase.instance.bookDao().insert(state.value.book)
        }
    }
}