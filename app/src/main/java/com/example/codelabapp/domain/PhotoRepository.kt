package com.example.codelabapp.domain

import com.example.codelabapp.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    fun getAllPhotos(): Flow<List<Photo>>
}