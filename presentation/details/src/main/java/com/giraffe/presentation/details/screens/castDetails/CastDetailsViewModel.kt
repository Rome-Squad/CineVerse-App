package com.giraffe.presentation.details.screens.castDetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.usecase.AddRecentPersonUseCase
import com.giraffe.media.person.usecase.GetPersonDetailsUseCase
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.navigation.routes.CastDetailsRoute
import com.giraffe.presentation.details.screens.castCredit.MediaType
import com.giraffe.presentation.details.utils.toSocialMediaUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.giraffe.user.exception.NoInternetException as UserNoInternetException

@HiltViewModel
class CastDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val storeRecentSeriesUseCase: AddRecentPersonUseCase
) : BaseViewModel<CastDetailsUiState, CastDetailsEffect>(
    CastDetailsUiState(
        actorId = savedStateHandle.toRoute<CastDetailsRoute>().id
    )
),
    CastDetailsInteractionListener {

    init {
        getPersonDetails(state.value.actorId)
    }

    private fun getPersonDetails(personId: Int) {
        updateState { it.copy(isLoading = true) }

        safeExecute(
            onSuccess = ::getPersonDetailsSuccess,
            onError = ::getPersonDetailsError,
            block = { getPersonDetailsUseCase(personId) }
        )
    }

    private fun getPersonDetailsSuccess(person: Person) {
        safeExecute { storeRecentSeriesUseCase(person) }

        updateState {
            it.copy(
                actorId = person.id,
                isLoading = false,
                isNoInternet = false,
                actorImageUrl = person.imageUrl.orEmpty(),
                actorName = person.name,
                actorBirth = person.birthday.orEmpty(),
                actorPlace = person.placeOfBirth.orEmpty(),
                actorGalleryImageUrls = person.images,
                biographyInfo = person.biography.orEmpty(),
                socialMediaUiList = person.socialMedia?.toSocialMediaUi() ?: emptyList(),
                posters = person.personCredits.map { personCredit ->
                    Poster(
                        id = personCredit.id,
                        name = personCredit.title,
                        imageUri = personCredit.posterPath.orEmpty(),
                        rating = personCredit.voteAverage.toFloat(),
                        mediaTypeOfPoster = personCredit.mediaType
                    )
                }
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
            MediaType.MOVIE.value ->
                sendEffect(CastDetailsEffect.NavigateToMovieDetails(mediaId))

            MediaType.TV.value ->
                sendEffect(CastDetailsEffect.NavigateToSeriesDetails(mediaId))
        }
    }

    override fun onBackClick() {
        sendEffect(CastDetailsEffect.NavigateBack)
    }
}