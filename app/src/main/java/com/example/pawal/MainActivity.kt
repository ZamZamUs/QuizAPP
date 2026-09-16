package com.example.pawal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.pawal.data.local.database.AppDatabase
import com.example.pawal.data.repository.QuizRepository
import com.example.pawal.ui.navigation.NavGraph
import com.example.pawal.ui.theme.PawalTheme
import com.example.pawal.viewmodel.QuizViewModel
import com.example.pawal.viewmodel.QuizViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = QuizRepository(database.quizDao())
        val viewModelFactory = QuizViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[QuizViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            PawalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
