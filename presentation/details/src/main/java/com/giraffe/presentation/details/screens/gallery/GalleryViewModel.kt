package com.giraffe.presentation.details.screens.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.media.person.usecase.GetPersonImagesUseCase
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.navigation.routes.GalleryRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val getPersonImagesUseCase: GetPersonImagesUseCase
) : BaseViewModel<GalleryUiState, Any>(
    GalleryUiState(
        actorId = savedStateHandle.toRoute<GalleryRoute>().personId,
        actorName = savedStateHandle.toRoute<GalleryRoute>().actorName,
    )
) {

    init {
        getPersonImages()
    }


    private fun getPersonImages() {
        updateState {
            it.copy(isLoading = true)
        }
        safeExecute(
            onSuccess = ::getPersonImagesSuccess,
            onError = ::getPersonImagesError
        ) {
            state.value.actorId?.let { personId ->
                getPersonImagesUseCase(personId)
            } ?: emptyList()
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

    private fun getPersonImagesSuccess(images: List<String>) {
        updateState {
            it.copy(
                isLoading = false,
                imageUrls = images
            )
        }
    }
}