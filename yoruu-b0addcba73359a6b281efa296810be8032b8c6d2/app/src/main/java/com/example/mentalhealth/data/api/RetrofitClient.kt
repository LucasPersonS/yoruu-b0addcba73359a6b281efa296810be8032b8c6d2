package com.example.mentalhealth.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val QUOTABLE_BASE_URL = "https://api.quotable.io/"
    private const val ADVICE_BASE_URL = "https://api.adviceslip.com/"
    private const val BORED_BASE_URL = "https://www.boredapi.com/api/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private val quotableRetrofit = Retrofit.Builder()
        .baseUrl(QUOTABLE_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val adviceRetrofit = Retrofit.Builder()
        .baseUrl(ADVICE_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val boredRetrofit = Retrofit.Builder()
        .baseUrl(BORED_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val quotableService: ApiService = quotableRetrofit.create(ApiService::class.java)
    val adviceService: ApiService = adviceRetrofit.create(ApiService::class.java)
    val boredService: ApiService = boredRetrofit.create(ApiService::class.java)
} 