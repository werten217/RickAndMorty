package com.example.rickandmorty.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val characterApiService: CharacterApiService by lazy {
        retrofit.create(CharacterApiService::class.java)
    }

    val episodeApiService: EpisodeApiService by lazy {
        retrofit.create(EpisodeApiService::class.java)
    }

    val locationApiService: LocationApiService by lazy {
        retrofit.create(LocationApiService::class.java)
    }
}