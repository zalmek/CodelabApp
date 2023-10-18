package com.example.codelabapp.network

import com.example.codelabapp.model.Photo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

interface CodelabApi {

    @GET("photos")
    suspend fun getAllPhotos(): List<Photo>

    companion object RetrofitBuilder{
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val api: CodelabApi = getRetrofit().create()
    }

}