package com.example.codelabapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codelabapp.domain.PhotoRepositoryImpl
import com.example.codelabapp.model.Photo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhotoViewModel (private val photoRepository: PhotoRepositoryImpl = PhotoRepositoryImpl()) : ViewModel() {
    private val _photoUiState = MutableStateFlow(emptyList<Photo>())
    val photoUiState = _photoUiState.asStateFlow()

    fun getAllPhotos() {
        viewModelScope.launch(CoroutineExceptionHandler{ _, exception ->
            println("CoroutineExceptionHandler got $exception") }) {
            photoRepository.getAllPhotos()
                .collect { photos ->
                    _photoUiState.value = photos
                }
        }
    }
}