package com.giraffe.presentation.details.screens.castDetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.media.mediaMember.entity.CastMember
import com.giraffe.media.mediaMember.usecase.AddCastToRecentCastUseCase
import com.giraffe.media.mediaMember.usecase.GetCastDetailsUseCase
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.navigation.routes.CastDetailsRoute
import com.giraffe.presentation.details.screens.castCredit.MediaType
import com.giraffe.presentation.details.utils.toSocialMediaUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CastDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCastDetailsUseCase: GetCastDetailsUseCase,
    private val storeRecentSeriesUseCase: AddCastToRecentCastUseCase
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
            block = { getCastDetailsUseCase(personId) }
        )
    }

    private fun getPersonDetailsSuccess(castMember: CastMember) {
        safeExecute { storeRecentSeriesUseCase(castMember) }

        //todo we should get posters from new usecases which will get movies and series for personId
        updateState {
            it.copy(
                actorId = castMember.id,
                isLoading = false,
                actorImageUrl = castMember.imageUrl.orEmpty(),
                actorName = castMember.name,
                actorBirth = castMember.birthday.orEmpty(),
                actorPlace = castMember.placeOfBirth.orEmpty(),
                actorGalleryImageUrls = castMember.otherImages.orEmpty(),
                biographyInfo = castMember.biography.orEmpty(),
                socialMediaUiList = castMember.socialMedia?.toSocialMediaUi() ?: emptyList(),
                posters = emptyList(),

//                    castMember.personCredits.map { personCredit ->
//                    Poster(
//                        id = personCredit.id,
//                        name = personCredit.title,
//                        imageUrl = personCredit.posterPath.orEmpty(),
//                        rating = personCredit.voteAverage.toFloat(),
//                        mediaTypeOfPoster = personCredit.mediaType
//                    )
//                }
            )
        }
    }

    private fun getPersonDetailsError(exception: Throwable) {
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
            MediaType.MOVIE.value ->
                sendEffect(CastDetailsEffect.NavigateToMovieDetails(mediaId))
            MediaType.TV.value ->
                sendEffect(CastDetailsEffect.NavigateToSeriesDetails(mediaId))
        }
    }

    override fun onBackArrowClick() {
        sendEffect(CastDetailsEffect.NavigateUp)
    }
}