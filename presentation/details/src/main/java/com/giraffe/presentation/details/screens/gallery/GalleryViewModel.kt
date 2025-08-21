package com.giraffe.presentation.details.screens.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.mediaMember.usecase.GetMediaMemberImageUrlsUseCase
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.navigation.routes.GalleryRoute
import com.giraffe.user.usecase.GetContentPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.giraffe.user.exception.NoInternetException as UserNoInternetException

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getContentPreferenceUseCase: GetContentPreferenceUseCase,
    savedStateHandle: SavedStateHandle,
    private val getMediaMemberImageUrlsUseCase: GetMediaMemberImageUrlsUseCase
) :BaseViewModel<GalleryUiState, GalleryEffect>(
    GalleryUiState(
        actorId = savedStateHandle.toRoute<GalleryRoute>().personId,
        actorName = savedStateHandle.toRoute<GalleryRoute>().actorName,
    )
), GalleryInteractionListener {

    init {
        observeContentPreference()
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
                getMediaMemberImageUrlsUseCase(personId)
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
    private fun observeContentPreference() {
        safeCollect(
            onEmitNewValue = { preference ->
                updateState { it.copy(contentPreference = preference) }
            },
            block = getContentPreferenceUseCase::invoke
        )
    }
    override fun onBackClick() {
        sendEffect(GalleryEffect.NavigateBack)
    }

    override fun onRetryClick() {
        getPersonImages()
    }
}