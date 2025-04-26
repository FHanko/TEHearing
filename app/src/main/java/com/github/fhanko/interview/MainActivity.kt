package com.github.fhanko.interview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.fhanko.interview.book_add.BookAddScreen
import com.github.fhanko.interview.book_add.BookAddViewModel
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
                BookNavigation()
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
            val itemId = backStackEntry.arguments?.getString("itemId")
        }
    }
}