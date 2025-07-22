package com.giraffe.details.screens.castDetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.navigation.CastDetailsRoute
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.usecase.GetPersonDetailsUseCase

class CastDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    val getPersonDetailsUseCase: GetPersonDetailsUseCase
) : BaseViewModel<CastDetailsUiState, CastDetailsEffect>(initialState = CastDetailsUiState()),
    CastDetailsInteractionListener {
    val personId = savedStateHandle.toRoute<CastDetailsRoute>().personId

    init {
        getPersonDetails(personId)
    }

    override fun onActorYoutubeLinkClicked() {
        sendEffect(CastDetailsEffect.OpenUrl(state.value.actorYouTubeLink))
    }

    override fun onActorFacebookLinkClicked() {
        sendEffect(CastDetailsEffect.OpenUrl(state.value.actorFacebookLink))
    }

    override fun onActorInstagramLinkClicked() {
        sendEffect(CastDetailsEffect.OpenUrl(state.value.actorInstagramLink))
    }

    override fun onMovieClicked(movieId: Int) {
        TODO("Not yet implemented")
    }

    override fun navigateToMoviesListScreen() {
        sendEffect(CastDetailsEffect.NavigateToMovies)
    }

    override fun navigateToActorGalleryScreen() {
        sendEffect(
            CastDetailsEffect.NavigateToGallery(
                actorName = state.value.actorName,
                imageUrls = state.value.actorGalleryImageUrls
            )
        )
    }

    private fun getPersonDetails(personId: Int) {
        safeExecute(
            onSuccess = ::getPersonDetailsSuccess,
            onError = ::getPersonDetailsError
        ) {
            updateState { it.copy(isLoading = true) }
            getPersonDetailsUseCase.invoke(personId)
        }
    }

    private fun getPersonDetailsSuccess(person: Person) {
        updateState {
            it.copy(
                isLoading = false,
                actorImageUrl = person.imageUrl.orEmpty(),
                actorName = person.name,
                actorBirth = person.birthday.orEmpty(),
                actorPlace = person.placeOfBirth.orEmpty(),
                actorGalleryImageUrls = person.images,
                biographyInfo = person.biography.orEmpty(),
                actorFacebookLink = person.socialMedia?.facebookLink.orEmpty(),
                actorInstagramLink = person.socialMedia?.instagramLink.orEmpty(),
                actorYouTubeLink = person.socialMedia?.youtubeLink.orEmpty(),
                posters = person.personCredits.map { personCredit ->
                    Poster(
                        id = personCredit.id,
                        name = personCredit.title,
                        imageUri = personCredit.posterPath.orEmpty(),
                        rating = personCredit.voteAverage.toFloat(),
                    )
                }
            )
        }
    }

    private fun getPersonDetailsError(exception: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEffect(CastDetailsEffect.Error(exception))
    }

}