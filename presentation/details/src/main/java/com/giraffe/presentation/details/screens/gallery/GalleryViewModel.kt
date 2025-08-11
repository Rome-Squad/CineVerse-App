package com.giraffe.presentation.details.screens.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.media.exception.NoInternetException
import com.giraffe.user.exception.NoInternetException as UserNoInternetException
import com.giraffe.media.mediaMember.usecase.GetMediaMemberImagesUseCase
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.navigation.routes.GalleryRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMediaMemberImagesUseCase: GetMediaMemberImagesUseCase
) :BaseViewModel<GalleryUiState, GalleryEffect>(
    GalleryUiState(
        actorId = savedStateHandle.toRoute<GalleryRoute>().personId,
        actorName = savedStateHandle.toRoute<GalleryRoute>().actorName,
    )
), GalleryInteractionListener {

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
                getMediaMemberImagesUseCase(personId)
            } ?: emptyList()
        }
    }

    private fun getPersonImagesError(error: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = error is NoInternetException ||
                        error is UserNoInternetException
            )
        }

        sendEffect(GalleryEffect.ShowError(error))
    }

    private fun getPersonImagesSuccess(images: List<String>) {
        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = false,
                imageUrls = images
            )
        }
    }

    override fun onBackClick() {
        sendEffect(GalleryEffect.NavigateBack)
    }
}