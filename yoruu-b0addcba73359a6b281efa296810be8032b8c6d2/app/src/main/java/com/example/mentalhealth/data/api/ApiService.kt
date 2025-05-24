package com.example.mentalhealth.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("https://api.quotable.io/random")
    suspend fun getRandomQuote(): QuoteResponse

    @GET("https://api.adviceslip.com/advice")
    suspend fun getAdvice(): AdviceResponse

    @GET("https://www.boredapi.com/api/activity")
    suspend fun getActivity(
        @Query("type") type: String? = null,
        @Query("participants") participants: Int? = null
    ): ActivityResponse
}

data class QuoteResponse(
    val content: String,
    val author: String
)

data class AdviceResponse(
    val slip: AdviceSlip
)

data class AdviceSlip(
    val id: Int,
    val advice: String
)

data class ActivityResponse(
    val activity: String,
    val type: String,
    val participants: Int,
    val price: Double,
    val link: String,
    val key: String,
    val accessibility: Double
) 