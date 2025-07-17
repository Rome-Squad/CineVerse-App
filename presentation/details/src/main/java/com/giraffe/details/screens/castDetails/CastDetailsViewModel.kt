package com.giraffe.details.screens.castDetails

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.base.BaseViewModel
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.usecase.GetPersonDetailsUseCase

class CastDetailsViewModel(
    personId: Int,
    val getPersonDetailsUseCase: GetPersonDetailsUseCase
) : BaseViewModel<CastDetailsUiState, CastDetailsEffect>(initialState = CastDetailsUiState()),
    CastDetailsInteractionListener {

    init {
        getPersonDetails(personId)
    }

    override fun onActorYoutubeLinkClicked() {
        TODO("Not yet implemented")
    }

    override fun onActorFacebookLinkClicked() {
        TODO("Not yet implemented")
    }

    override fun onActorInstagramLinkClicked() {
        TODO("Not yet implemented")
    }

    override fun onMovieClicked(movieId: Int) {
        TODO("Not yet implemented")
    }

    override fun navigateToMoviesListScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToActorGalleryScreen() {
        TODO("Not yet implemented")
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