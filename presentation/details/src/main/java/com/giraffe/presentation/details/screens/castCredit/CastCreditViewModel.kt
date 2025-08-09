package com.giraffe.presentation.details.screens.castCredit

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.media.movies.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.person.entity.PersonCredit
import com.giraffe.media.person.usecase.GetPeopleMediaCreditsUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.utils.toPoster
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CastCreditViewModel @Inject constructor(
    private val getPeopleMediaCredits: GetPeopleMediaCreditsUseCase,
    private val getSeriesGenres: GetSeriesGenresByIdsUseCase,
    private val getMoviesGenresByIds: GetMoviesGenresByIdsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CastCreditScreenState, CastCreditEffect>(initialState = CastCreditScreenState()),
    CastCreditInteractionListener {

    init {
        val castId = savedStateHandle.toRoute<CastCreditRoute>().castID
        val actorName = savedStateHandle.toRoute<CastCreditRoute>().actorName

        updateState {
            it.copy(
                castId = castId,
                actorName = actorName
            )
        }

        loadCastCredit(castId)
    }

    private fun loadCastCredit(castId: Int) {
        updateState { it.copy(isLoading = true) }

        safeExecute(
            onSuccess = ::loadCastCreditSuccess,
            onError = ::loadCastCreditError
        ) {
            getPeopleMediaCredits(castId)
        }
    }

    private fun loadCastCreditSuccess(personCredits: List<PersonCredit>) {
        safeExecute(
            onError = ::loadCastCreditError,
            onSuccess = { posters ->
                updateState {
                    it.copy(
                        isLoading = false,
                        posters = posters,
                    )
                }
            }
        ) {

            personCredits.map { personCredit ->
                val mediaType = MediaType.from(personCredit.mediaType)

                val genres = when (mediaType) {
                    MediaType.MOVIE -> getMoviesGenresByIds(personCredit.genreIds)
                    MediaType.TV -> getSeriesGenres(personCredit.genreIds)
                    else -> emptyList()
                }

                personCredit.toPoster().copy(genres = genres.joinToString(", "))
            }

        }
    }


    private fun loadCastCreditError(exception: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEffect(CastCreditEffect.Error(exception))
    }

    override fun onPosterClick(mediaId: Int, mediaType: String) {
        sendEffect(
            effect = when (mediaType) {
                MediaType.MOVIE.value -> CastCreditEffect.NavigateToMovieDetails(mediaId)
                MediaType.TV.value -> CastCreditEffect.NavigateToSeriesDetails(mediaId)
                else -> return
            }
        )
    }

    override fun changeView(isGrid: Boolean) {
        updateState { it.copy(isGridSelected = isGrid) }
    }
}