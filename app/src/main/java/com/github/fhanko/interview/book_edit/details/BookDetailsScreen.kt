package com.github.fhanko.interview.book_edit.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.fhanko.interview.R
import com.github.fhanko.interview.book_edit.BookEditColumn
import com.github.fhanko.interview.book_list.ToggleReadButton
import com.github.fhanko.interview.book_list.readMap

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
                        Spacer(modifier = Modifier.width(50.dp))
                        DeleteButton {
                            viewModel.call(BookDetailsIntent.RemoveBook)
                            navigation.popBackStack()
                        }
                    }
                }
        }
    }
}

@Composable
fun DeleteButton(onDelete: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Filled.Clear, "Delete",
            modifier = Modifier
                .size(90.dp, 44.dp)
                .align(Alignment.CenterHorizontally)
                .clickable { onDelete() }
        )
        Text(
            text = "Delete",
            style = MaterialTheme.typography.labelSmall.merge(color = Color.Gray),
            textAlign = TextAlign.Center
        )
    }
}