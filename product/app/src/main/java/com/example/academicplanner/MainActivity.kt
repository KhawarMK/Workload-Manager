package com.example.academicplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.academicplanner.data.local.AppDatabase
import com.example.academicplanner.data.repository.ModuleRepository
import com.example.academicplanner.ui.modules.ModuleScreen
import com.example.academicplanner.ui.modules.ModuleViewModel
import com.example.academicplanner.ui.modules.ModuleViewModelFactory
import com.example.academicplanner.ui.theme.AcademicPlannerTheme

class MainActivity : ComponentActivity() {

    // 1. Initialize the Room SQLite database
    private val database by lazy { AppDatabase.getDatabase(this) }

    // 2. Initialize the repository (single source of truth)
    private val repository by lazy { ModuleRepository(database.moduleDao()) }

    // 3. Initialize the ViewModel using our custom Factory
    private val viewModel: ModuleViewModel by viewModels {
        ModuleViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AcademicPlannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Display our Module CRUD screen with safe padding
                    ModuleScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}