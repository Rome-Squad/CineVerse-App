package com.giraffe.presentation.details.screens.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.media.mediaMember.usecase.GetPersonImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val getPersonImagesUseCase: GetPersonImagesUseCase
) : BaseViewModel<GalleryUiState, Any>(GalleryUiState()) {
    private val personId = savedStateHandle.toRoute<GalleryRoute>().personId
    private val actorName = savedStateHandle.toRoute<GalleryRoute>().actorName

    init {
        initializeActorName()
        getPersonImages(personId)
    }

    private fun initializeActorName() {
        updateState {
            it.copy(actorName = actorName)
        }
    }

    private fun getPersonImages(personId: Int) {
        updateState {
            it.copy(isLoading = true)
        }
        safeExecute(
            onSuccess = ::getPersonImagesSuccess,
            onError = ::getPersonImagesError
        ) {
            getPersonImagesUseCase.invoke(personId)
        }
    }

    private fun getPersonImagesError(error: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
                errorMessage = error.message
            )
        }
    }

    private fun getPersonImagesSuccess(images: List<String?>) {
        updateState {
            it.copy(
                isLoading = false,
                imageUrls = images
            )
        }
    }
}