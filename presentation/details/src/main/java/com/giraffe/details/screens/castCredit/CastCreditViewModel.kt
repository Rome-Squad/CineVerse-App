package com.giraffe.details.screens.castCredit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.screens.castCredit.CastCreditEffect.Error
import com.giraffe.details.screens.castCredit.CastCreditEffect.NavigateToMovieDetails
import com.giraffe.details.screens.castCredit.CastCreditEffect.NavigateToSeriesDetails
import com.giraffe.details.utils.toPoster
import com.giraffe.media.movies.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.person.entity.PersonCredit
import com.giraffe.media.person.usecase.GetPeopleMediaCreditsUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastCreditViewModel @Inject constructor(
    private val getPeopleMediaCredits: GetPeopleMediaCreditsUseCase,
    private val getSeriesGenres: GetSeriesGenresByIdsUseCase,
    private val getMoviesGenresByIds: GetMoviesGenresByIdsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CastCreditScreenState, CastCreditEffect>(initialState = CastCreditScreenState()),
    CastCreditInteractionListener {

    val castId = savedStateHandle.toRoute<CastCreditRoute>().castID
    val actorName = savedStateHandle.toRoute<CastCreditRoute>().actorName

    init {
        loadCastCredit(castId)
    }

    private fun loadCastCredit(castId: Int) {
        safeExecute(
            onSuccess = ::loadCastCreditSuccess,
            onError = ::loadCastCreditError
        ) {
            updateState { it.copy(isLoading = true) }
            getPeopleMediaCredits(castId)
        }
    }

    private fun loadCastCreditSuccess(personCredits: List<PersonCredit>) {
        safeExecute {
            val posters = loadPostersOfCastCredit(personCredits)
            updateState {
                it.copy(
                    isLoading = false,
                    posters = posters,
                    actorName = actorName
                )
            }
        }
    }

    private suspend fun loadPostersOfCastCredit(personCredits: List<PersonCredit>) =
        coroutineScope {
            personCredits.map { personCredit ->
                async {
                    val mediaType = MediaType.from(personCredit.mediaType)
                    val genres = when (mediaType) {
                        MediaType.MOVIE -> getMoviesGenresByIds(personCredit.genreIds)
                            .map { it.title }

                        MediaType.TV -> getSeriesGenres(personCredit.genreIds)
                            .map { it.title }

                        else -> emptyList()
                    }
                    personCredit.toPoster().copy(genres = genres.joinToString(", "))
                }
            }.awaitAll()
        }


    private fun loadCastCreditError(exception: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEffect(Error(exception))
    }

    override fun onPosterClick(mediaId: Int, mediaType: String) {
        viewModelScope.launch {
            sendEffect(
                when (mediaType) {
                    MediaType.MOVIE.value -> NavigateToMovieDetails(mediaId)
                    MediaType.TV.value -> NavigateToSeriesDetails(mediaId)
                    else -> return@launch
                }
            )
        }
    }

    override fun changeView(isGrid: Boolean) {
        updateState { it.copy(isGridSelected = isGrid) }
    }
}