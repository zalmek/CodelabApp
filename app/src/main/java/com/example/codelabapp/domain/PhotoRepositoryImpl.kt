package com.example.codelabapp.domain

import com.example.codelabapp.model.Photo
import com.example.codelabapp.network.ApiHelper
import com.example.codelabapp.network.CodelabApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class PhotoRepositoryImpl() : PhotoRepository {
    override fun getAllPhotos(): Flow<List<Photo>> =
        callbackFlow {
            trySendBlocking(
                ApiHelper(CodelabApi.api).getAllPhotos()
            )
            awaitClose()}
}