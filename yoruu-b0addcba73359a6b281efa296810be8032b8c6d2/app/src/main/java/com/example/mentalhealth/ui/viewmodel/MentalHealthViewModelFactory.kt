package com.example.mentalhealth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mentalhealth.data.repository.MentalHealthRepository

class MentalHealthViewModelFactory(
    private val repository: MentalHealthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MentalHealthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MentalHealthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 