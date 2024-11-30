package com.unilasalle.td.services.network

import com.unilasalle.td.services.network.datas.Image
import com.unilasalle.td.services.network.datas.Joke
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Interface for the API service
 */
interface ApiService {
    @GET("random_joke")
    suspend fun getRandomJoke(): Joke

    @GET("breeds/image/random")
    suspend fun getRandomImage(): Image

    companion object {
        private const val JOKE_BASE_URL = "https://official-joke-api.appspot.com/"
        private const val IMAGE_BASE_URL = "https://dog.ceo/api/"

        // Create a joke service
        fun createJokeService(): ApiService {
            return Retrofit.Builder()
                .baseUrl(JOKE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

        // Create an image service
        fun createImageService(): ApiService {
            return Retrofit.Builder()
                .baseUrl(IMAGE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}