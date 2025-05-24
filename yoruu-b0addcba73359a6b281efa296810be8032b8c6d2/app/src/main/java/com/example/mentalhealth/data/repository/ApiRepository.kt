package com.example.mentalhealth.data.repository

import com.example.mentalhealth.data.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApiRepository(private val apiService: ApiService) {
    suspend fun getRandomQuote() = withContext(Dispatchers.IO) {
        try {
            apiService.getRandomQuote()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getAdvice() = withContext(Dispatchers.IO) {
        try {
            apiService.getAdvice()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getActivity(type: String? = null, participants: Int? = null) = withContext(Dispatchers.IO) {
        try {
            apiService.getActivity(type, participants)
        } catch (e: Exception) {
            null
        }
    }
} 