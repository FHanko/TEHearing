package com.github.fhanko.interview.book_edit.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.fhanko.interview.book_edit.BookEditColumn
import com.github.fhanko.interview.book_list.ToggleReadButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(viewModel: BookDetailsViewModel, navigation: NavController) {
    viewModel.call(BookDetailsIntent.BookInit)

    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Book Details")},
            navigationIcon = { IconButton(onClick = { navigation.popBackStack() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, "Back")
            } })
        }
    ) { innerPadding ->
        when {
            state.isLoading ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            else ->
                BookEditColumn(state.book, viewModel, Modifier.padding(innerPadding)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp),
                        horizontalArrangement = Arrangement.Center) {
                        ToggleReadButton(readState = state.book.readState) {
                            viewModel.call(BookDetailsIntent.ToggleReadState)
                        }
                    }
                }
        }
    }
}