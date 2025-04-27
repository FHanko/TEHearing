package com.github.fhanko.interview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.fhanko.interview.book_edit.add.BookAddScreen
import com.github.fhanko.interview.book_edit.add.BookAddViewModel
import com.github.fhanko.interview.book_edit.details.BookDetailsScreen
import com.github.fhanko.interview.book_edit.details.BookDetailsViewModel
import com.github.fhanko.interview.book_list.BookListScreen
import com.github.fhanko.interview.book_list.BookListViewModel
import com.github.fhanko.interview.ui.theme.InterviewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppDatabase.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            InterviewTheme {
                Surface {
                    BookNavigation()
                }
            }
        }
    }
}

@Composable
fun BookNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { BookListScreen(BookListViewModel(), navController) }
        composable("add") { BookAddScreen(BookAddViewModel(), navController) }
        composable("details/{itemId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("itemId")
            BookDetailsScreen(BookDetailsViewModel(bookId!!.toInt()), navController)
        }
    }
}