package com.giraffe.details.screens.castCredit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.screens.castCredit.CastCreditEffect.Error
import com.giraffe.details.screens.castCredit.CastCreditEffect.NavigateToMovieDetails
import com.giraffe.details.screens.castCredit.CastCreditEffect.NavigateToSeriesDetails
import com.giraffe.media.movies.usecase.GetMovieGenresUseCase
import com.giraffe.media.person.entity.PersonCredit
import com.giraffe.media.person.usecase.GetPeopleMediaCreditsUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class CastCreditViewModel(
    private val getPeopleMediaCredits: GetPeopleMediaCreditsUseCase,
    private val getSeriesGenres: GetSeriesGenresByIdsUseCase,
    private val getMovieGenres: GetMovieGenresUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CastCreditScreenState, CastCreditEffect>(initialState = CastCreditScreenState()),
    CastCreditInteractionListener {

    val castId = savedStateHandle.toRoute<CastCreditRoute>().castID

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
            val posters = coroutineScope {
                personCredits.map { personCredit ->
                    async {
                        val mediaType = MediaType.from(personCredit.mediaType)
                        val genres = when (mediaType) {
                            MediaType.MOVIE -> getMovieGenres(personCredit.genreIds)
                                .map { it.title }

                            MediaType.TV -> getSeriesGenres(personCredit.genreIds)
                                .map { it.title }

                            else -> emptyList()
                        }


                        Poster(
                            id = personCredit.id,
                            name = personCredit.title,
                            imageUri = personCredit.posterPath.toString(),
                            rating = personCredit.voteAverage.toFloat(),
                            genres = genres.joinToString(", "),
                            date = personCredit.releaseYear.toString(),
                            mediaTypeOfPoster = personCredit.mediaType
                        )
                    }
                }.awaitAll()
            }
            updateState {
                it.copy(
                    isLoading = false,
                    posters = posters
                )
            }
        }
    }


    private fun loadCastCreditError(exception: Throwable) {
        safeExecute {
            updateState { it.copy(isLoading = false) }
            sendEffect(Error(exception))
        }
    }

    override fun onPosterClick(mediaId: Int, mediaType: String) {
        safeExecute {
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
    }

    override fun changeView(isGrid: Boolean) {
        safeExecute { updateState { it.copy(isGridSelected = isGrid) } }
    }

}