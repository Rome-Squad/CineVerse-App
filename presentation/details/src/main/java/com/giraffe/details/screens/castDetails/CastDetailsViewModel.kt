package com.giraffe.details.screens.castDetails

import android.util.Log
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.base.BaseViewModel
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.usecase.GetPersonDetailsUseCase

class CastDetailsViewModel(
    val getPersonDetailsUseCase: GetPersonDetailsUseCase
) : BaseViewModel<CastDetailsUiState, CostDetailsEffect>(CastDetailsUiState()),
    CastDetailsInteractionListener {

    init {
        getPersonDetails()
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

    private fun getPersonDetails(personId: Int = 1) = safeExecute(
        block = {
            updateState { it.copy(isLoading = true) }
            getPersonDetailsUseCase.invoke(personId)
        },
        onSuccess = ::getPersonDetailsSuccess,
        onError = ::getPersonDetailsError
    )

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
                //should be edit this line to use the correct response
                posters = person.movieCredits.map { person ->
                    Poster(
                        id = person.id,
                        name = person.title,
                        imageUri = person.posterPath.orEmpty(),
                        rating = person.voteAverage.toFloat(),
                    )
                }
            )
        }
    }

    private fun getPersonDetailsError(exception: Throwable) {
        updateState { it.copy(isLoading = false) }
        Log.d("Error", "getPersonDetailsError: ${exception.message}")
    }

}