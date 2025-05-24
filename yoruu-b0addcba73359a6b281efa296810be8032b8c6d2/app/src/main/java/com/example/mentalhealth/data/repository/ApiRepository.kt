package com.example.mentalhealth.data.repository

import com.example.mentalhealth.data.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

class ApiRepository(private val apiService: ApiService) {
    private val apiKey = "YOUR_API_NINJAS_KEY" // Replace with your actual API key

    suspend fun getRandomQuote(): QuoteResponse? = withContext(Dispatchers.IO) {
        try {
            withTimeoutOrNull(5000) {
                apiService.getRandomQuote()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getAdvice(): AdviceResponse? = withContext(Dispatchers.IO) {
        try {
            withTimeoutOrNull(5000) {
                apiService.getAdvice()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getActivity(): ActivityResponse? = withContext(Dispatchers.IO) {
        try {
            withTimeoutOrNull(5000) {
                apiService.getActivity()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
} 