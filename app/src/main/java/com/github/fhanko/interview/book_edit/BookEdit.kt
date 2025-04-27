package com.github.fhanko.interview.book_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.github.fhanko.interview.Book

@Composable
fun BookEditColumn(
    book: Book,
    viewModel: BookEditViewModel,
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        BookInput(value = book.title, placeholder = "Title*") {
            viewModel.call(BookEditIntent.InputChanged(InputField.Title, it))
        }
        BookInput(value = book.author, placeholder = "Author*") {
            viewModel.call(BookEditIntent.InputChanged(InputField.Author, it))
        }
        BookInput(value = book.isbn, placeholder = "ISBN") {
            viewModel.call(BookEditIntent.InputChanged(InputField.ISBN, it))
        }
        BookInput(value = book.notes, placeholder = "Notes", 4) {
            viewModel.call(BookEditIntent.InputChanged(InputField.Notes, it))
        }
        content()
    }
}

@Composable
fun BookInput(value: String?, placeholder: String, minLines: Int = 1, onChange: (String) -> Unit) {
    Row {
        TextField(
            value = value ?: "",
            label = { Text(text = placeholder) },
            modifier = Modifier.fillMaxWidth(),
            minLines = minLines,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            onValueChange = {
                onChange(it)
            })
    }
    Spacer(modifier = Modifier.height(8.dp))
}
