package com.example.mentalhealth.data.api

import retrofit2.http.GET
import retrofit2.http.Query

data class QuoteResponse(
    val content: String,
    val author: String
)

data class AdviceResponse(
    val slip: Slip
) {
    data class Slip(
        val id: Int,
        val advice: String
    )
}

data class ActivityResponse(
    val activity: String,
    val type: String,
    val participants: Int,
    val price: Double,
    val link: String,
    val key: String,
    val accessibility: Double
)

interface ApiService {
    @GET("random")
    suspend fun getRandomQuote(): QuoteResponse

    @GET("advice")
    suspend fun getAdvice(): AdviceResponse

    @GET("activity")
    suspend fun getActivity(
        @Query("type") type: String? = null,
        @Query("participants") participants: Int? = null
    ): ActivityResponse
} 