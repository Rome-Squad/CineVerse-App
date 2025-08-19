package com.giraffe.presentation.details.screens.castDetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.mediaMember.entity.CastMember
import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.usecase.AddCastToRecentCastUseCase
import com.giraffe.media.mediaMember.usecase.GetCastCreditsUseCase
import com.giraffe.media.mediaMember.usecase.GetCastDetailsUseCase
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.components.uimodel.Poster
import com.giraffe.presentation.details.navigation.routes.CastDetailsRoute
import com.giraffe.presentation.details.utils.toPoster
import com.giraffe.presentation.details.utils.toSocialMediaUi
import com.giraffe.presentation.details.utils.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.giraffe.user.exception.NoInternetException as UserNoInternetException

@HiltViewModel
class CastDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCastDetailsUseCase: GetCastDetailsUseCase,
    private val getCastCreditsUseCase: GetCastCreditsUseCase,
    private val storeRecentSeriesUseCase: AddCastToRecentCastUseCase
) : BaseViewModel<CastDetailsUiState, CastDetailsEffect>(
    CastDetailsUiState(
        actorId = savedStateHandle.toRoute<CastDetailsRoute>().id
    )
),
    CastDetailsInteractionListener {

    init {
        getCastDetailsAndCredits(state.value.actorId)
    }

    private fun getCastDetailsAndCredits(personId: Int) {
        updateState { it.copy(isLoading = true) }

        safeExecute(
            onSuccess = ::getPersonDetailsSuccess,
            onError = ::getPersonDetailsError,
            block = { getCastDetailsUseCase(personId) }
        )
        getCastCredits(state.value.actorId)
    }


    private fun getCastCredits(actorId: Int) {
        safeExecute(
            onSuccess = ::getCastCreditsSuccess,
            onError = ::getCastCreditsError,
            block = { getCastCreditsUseCase(actorId) }
        )
    }

    private fun getPersonDetailsSuccess(castMember: CastMember) {
        safeExecute { storeRecentSeriesUseCase(castMember) }
        updateState {
            it.copy(
                actorId = castMember.id,
                isLoading = false,
                isNoInternet = false,
                actorImageUrl = castMember.imageUrl.orEmpty(),
                actorName = castMember.name,
                actorBirth = castMember.birthday.orEmpty(),
                actorPlace = castMember.placeOfBirth.orEmpty(),
                actorGalleryImageUrls = castMember.otherImages,
                biographyInfo = castMember.biography.orEmpty(),
                socialMediaUiList = castMember.socialMedia?.toSocialMediaUi() ?: emptyList(),
            )
        }
    }

    private fun getPersonDetailsError(exception: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = exception is NoInternetException ||
                        exception is UserNoInternetException
            )
        }

        sendEffect(CastDetailsEffect.ShowError(exception))
    }

    private fun getCastCreditsSuccess(media: MediaMemberRepository.CastMedia) {
        val posters = media.series.map { it.toUi().toPoster() }
            .plus(media.movies.map { it.toUi().toPoster() })

        updateState {
            it.copy(posters = posters)
        }
    }

    private fun getCastCreditsError(exception: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
            )
        }

        sendEffect(CastDetailsEffect.ShowError(exception))
    }


    override fun onSocialMediaLinkClick(url: String) {
        sendEffect(CastDetailsEffect.OpenUrl(url))
    }

    override fun onShowMoreGalleryTextClick() {
        sendEffect(
            CastDetailsEffect.NavigateToGallery(
                actorName = state.value.actorName,
                personId = state.value.actorId
            )
        )
    }

    override fun onShowMoreCastCreditsTextClick(castId: Int, actorName: String) {
        sendEffect(
            CastDetailsEffect.NavigateToCastCredit(
                castID = castId,
                actorName = actorName
            )
        )
    }

    override fun onPosterClick(mediaId: Int, mediaType: String) {
        when (mediaType) {
            Poster.Type.MOVIE.name ->
                sendEffect(CastDetailsEffect.NavigateToMovieDetails(mediaId))

            Poster.Type.SERIES.name ->
                sendEffect(CastDetailsEffect.NavigateToSeriesDetails(mediaId))
        }
    }

    override fun onBackClick() {
        sendEffect(CastDetailsEffect.NavigateBack)
    }

    override fun onRetryClick() {
        getCastDetailsAndCredits(state.value.actorId)
    }
}