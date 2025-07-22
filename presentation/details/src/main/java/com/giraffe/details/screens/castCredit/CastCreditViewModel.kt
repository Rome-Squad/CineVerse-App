package com.giraffe.details.screens.castCredit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.screens.castCredit.CastCreditEffect.Error
import com.giraffe.details.screens.castCredit.CastCreditEffect.NavigateToMovieDetails
import com.giraffe.details.screens.castCredit.CastCreditEffect.NavigateToSeriesDetails
import com.giraffe.media.movies.usecase.GetMovieGenresUseCase
import com.giraffe.media.person.entity.PersonCredit
import com.giraffe.media.person.usecase.GetPeopleMediaCreditsUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import kotlinx.coroutines.launch

class CastCreditViewModel(
    private val getPeopleMediaCredits: GetPeopleMediaCreditsUseCase,
    private val getSeriesGenres: GetSeriesGenresByIdsUseCase,
    private val getMovieGenres: GetMovieGenresUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CastCreditScreenState, CastCreditEffect>(initialState = CastCreditScreenState()),
    CastCreditInteractionListener {

    val castId = savedStateHandle.get<Int>("castID") ?: 0

    init {
        loadCastCredit(castId)
    }

    private fun loadCastCredit(castId: Int) {
        safeExecute(
            onSuccess = ::loadCastCreditSuccess,
            onError = ::loadCastCreditError
        ) {
            updateState { it.copy(isLoading = true) }
            getPeopleMediaCredits.invoke(castId)
        }
    }

    private fun loadCastCreditSuccess(personCredits: List<PersonCredit>) {

        updateState {
            it.copy(
                isLoading = false,
                posters = personCredits.map { personCredit ->
                    var genres = emptyList<String>()
                    viewModelScope.launch {
                        genres = when (personCredit.mediaType) {
                            "movie" -> getMovieGenres.invoke(personCredit.genreIds).map { it.title }
                            "tv" -> getSeriesGenres.invoke(personCredit.genreIds).map { it.title }
                            else -> emptyList()
                        }
                    }

                    Poster(
                        id = personCredit.id,
                        name = personCredit.title,
                        imageUri = personCredit.posterPath.toString(),
                        rating = personCredit.voteAverage.toFloat(),
                        genres = genres.joinToString(", "),
                        time = personCredit.duration.toString(),
                        date = personCredit.releaseYear.toString()
                    )
                }
            )
        }
    }

    private fun loadCastCreditError(exception: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEffect(Error(exception))
    }

    override fun onPosterClick(mediaId: Int, mediaType: String) {
        viewModelScope.launch {
            sendEffect(
                when (mediaType) {
                    "movie" -> NavigateToMovieDetails(mediaId)
                    "tv" -> NavigateToSeriesDetails(mediaId)
                    else -> return@launch
                }
            )

        }
    }

}