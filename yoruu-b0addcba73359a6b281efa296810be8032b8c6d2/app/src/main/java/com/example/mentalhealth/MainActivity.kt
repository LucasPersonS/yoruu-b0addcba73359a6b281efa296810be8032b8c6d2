package com.example.mentalhealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mentalhealth.data.database.AppDatabase
import com.example.mentalhealth.data.repository.MentalHealthRepository
import com.example.mentalhealth.ui.navigation.AppNavigation
import com.example.mentalhealth.ui.theme.MentalHealthTheme
import com.example.mentalhealth.ui.viewmodel.MentalHealthViewModel
import com.example.mentalhealth.ui.viewmodel.MentalHealthViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize database and repository
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = MentalHealthRepository(
            moodEntryDao = database.moodEntryDao(),
            userDao = database.userDao()
        )

        setContent {
            MentalHealthTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: MentalHealthViewModel = viewModel(
                        factory = MentalHealthViewModelFactory(repository)
                    )
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}
