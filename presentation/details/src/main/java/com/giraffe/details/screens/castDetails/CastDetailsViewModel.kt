package com.giraffe.details.screens.castDetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.screens.castCredit.MediaType
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.usecase.GetPersonDetailsUseCase
import com.giraffe.media.person.usecase.AddRecentPersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CastDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val storeRecentSeriesUseCase: AddRecentPersonUseCase
) : BaseViewModel<CastDetailsUiState, CastDetailsEffect>(initialState = CastDetailsUiState()),
    CastDetailsInteractionListener {
    private val personId: Int = savedStateHandle.toRoute<CastDetailsRoute>().id

    init {
        getPersonDetails(personId)
    }

    override fun onActorYoutubeLinkClicked() {
        state.value.socialMediaLinks?.youtubeLink?.let {
            sendEffect(CastDetailsEffect.OpenUrl(it))
        }
    }

    override fun onActorFacebookLinkClicked() {
        state.value.socialMediaLinks?.facebookLink?.let {
            sendEffect(CastDetailsEffect.OpenUrl(it))
        }
    }

    override fun onActorInstagramLinkClicked() {
        state.value.socialMediaLinks?.instagramLink?.let {
            sendEffect(CastDetailsEffect.OpenUrl(it))
        }
    }

    override fun onActorTwitterLinkClicked() {
        state.value.socialMediaLinks?.twitterLink?.let {
            sendEffect(CastDetailsEffect.OpenUrl(it))
        }
    }

    override fun onActorTikTokLinkClicked() {
        state.value.socialMediaLinks?.tiktokLink?.let {
            sendEffect(CastDetailsEffect.OpenUrl(it))
        }
    }

    override fun navigateToActorGalleryScreen() {
        sendEffect(
            CastDetailsEffect.NavigateToGallery(
                actorName = state.value.actorName,
                personId = state.value.actorId
            )
        )
    }

    override fun navigateToCastCreditScreen(castId: Int, actorName: String) {
        sendEffect(
            CastDetailsEffect.NavigateToCastCredit(
                castID = castId,
                actorName = actorName
            )
        )
    }

    override fun onPosterClick(mediaId: Int, mediaType: String) {
        when (mediaType) {
            MediaType.MOVIE.value -> sendEffect(CastDetailsEffect.NavigateToMovieDetails(mediaId))
            MediaType.TV.value -> sendEffect(CastDetailsEffect.NavigateToSeriesDetails(mediaId))
        }
    }

    private fun getPersonDetails(personId: Int) {
        safeExecute(
            onSuccess = ::getPersonDetailsSuccess,
            onError = ::getPersonDetailsError
        ) {
            updateState { it.copy(isLoading = true) }
            getPersonDetailsUseCase(personId)
        }
    }

    private fun getPersonDetailsSuccess(person: Person) {
        safeExecute { storeRecentSeriesUseCase(person) }

        updateState {
            it.copy(
                actorId = person.id,
                isLoading = false,
                actorImageUrl = person.imageUrl.orEmpty(),
                actorName = person.name,
                actorBirth = person.birthday.orEmpty(),
                actorPlace = person.placeOfBirth.orEmpty(),
                actorGalleryImageUrls = person.images,
                biographyInfo = person.biography.orEmpty(),
                socialMediaLinks = person.socialMedia?.toUiState(),
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
                errorMessage = exception.message
            )
        }
    }

}