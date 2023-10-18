package com.example.codelabapp.network

import com.example.codelabapp.model.Photo

class ApiHelper(private val codelabApi: CodelabApi) {
    suspend fun getAllPhotos(): List<Photo> {
        return codelabApi.getAllPhotos()
    }
}