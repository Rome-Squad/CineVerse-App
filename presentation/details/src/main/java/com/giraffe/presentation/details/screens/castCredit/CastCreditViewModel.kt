package com.giraffe.presentation.details.screens.castCredit

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.media.entity.Genre
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.usecase.GetCastCreditsUseCase
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.genre.ObserveMoviesGenresUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.genre.ObserveSeriesGenresUseCase
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.components.uimodel.Poster
import com.giraffe.presentation.details.navigation.routes.CastCreditRoute
import com.giraffe.presentation.details.utils.toPoster
import com.giraffe.presentation.details.utils.toUi
import com.giraffe.user.usecase.GetContentPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import com.giraffe.user.exception.NoInternetException as UserNoInternetException

@HiltViewModel
class CastCreditViewModel @Inject constructor(
    private val getPeopleMediaCredits: GetCastCreditsUseCase,
    private val observeSeriesGenresUseCase: ObserveSeriesGenresUseCase,
    private val observeMoviesGenresUseCase: ObserveMoviesGenresUseCase,
    private val getContentPreferenceUseCase: GetContentPreferenceUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CastCreditScreenState, CastCreditEffect>(
    CastCreditScreenState(
        castId = savedStateHandle.toRoute<CastCreditRoute>().castID,
        actorName = savedStateHandle.toRoute<CastCreditRoute>().actorName
    )
), CastCreditInteractionListener {

    init {
        updateState { it.copy(isNoInternet = false, isLoading = true) }
        observeContentPreference()
        loadSeriesGenres()
        loadMovieGenres()
        loadCastCredit()
    }

    private fun loadSeriesGenres() {
        safeCollect(
            onEmitNewValue = ::onSeriesGenresLoaded,
            onError = ::loadCastCreditError
        ) {
            observeSeriesGenresUseCase()
        }
    }

    private fun loadMovieGenres() {
        safeCollect(
            onEmitNewValue = ::onMovieGenresLoaded,
            onError = ::loadCastCreditError
        ) {
            observeMoviesGenresUseCase()
        }
    }

    private fun onSeriesGenresLoaded(series: List<Genre>) {
        updateState { it.copy(allSeriesGenres = series) }
    }

    private fun onMovieGenresLoaded(movies: List<Genre>) {
        updateState { it.copy(allMovieGenres = movies) }
    }

    private fun loadCastCredit() {
        safeExecute(
            onSuccess = ::loadCastCreditSuccess,
            onError = ::loadCastCreditError,
        ) {
            delay(500)
            getPeopleMediaCredits(state.value.castId)
        }
    }

    private fun loadCastCreditSuccess(castCredits: MediaMemberRepository.CastMedia) {
        addSeriesPosters(castCredits.series)
        addMoviePosters(castCredits.movies)
        updateState { it.copy(isLoading = false) }
    }

    private fun addMoviePosters(movies: List<Movie>) {
        val moviesPosters = movies.map {
            val genres = it.genresID.mapNotNull { id ->
                state.value.allMovieGenres.find { g -> g.id == id }?.title
            }
            it.toUi().copy(genres = genres).toPoster()
        }
        updateState {
            it.copy(
                posters = (moviesPosters + it.posters).distinctBy { poster -> poster.id }
                    .sortedByDescending { poster -> poster.date },
            )
        }
    }

    private fun addSeriesPosters(series: List<Series>) {
        val seriesPosters = series.map {
            val genres = it.genreIDs.mapNotNull { id ->
                state.value.allSeriesGenres.find { g -> g.id == id }?.title
            }
            it.toUi().copy(genres = genres).toPoster()
        }
        updateState {
            it.copy(
                posters = (seriesPosters + it.posters).distinctBy { poster -> poster.id }
                    .sortedByDescending { poster -> poster.date },
            )
        }
    }


    private fun loadCastCreditError(exception: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = exception is NoInternetException ||
                        exception is UserNoInternetException
            )
        }
        sendEffect(CastCreditEffect.Error(exception))
    }

    override fun onBackClick() {
        sendEffect(CastCreditEffect.NavigateBack)
    }

    override fun onPosterClick(mediaId: Int, mediaType: String) {
        sendEffect(
            effect = when (mediaType) {
                Poster.Type.MOVIE.name -> CastCreditEffect.NavigateToMovieDetails(mediaId)
                Poster.Type.SERIES.name -> CastCreditEffect.NavigateToSeriesDetails(mediaId)
                else -> return
            }
        )
    }

    private fun observeContentPreference() {
        safeCollect(
            onEmitNewValue = { preference ->
                updateState { it.copy(contentPreference = preference) }
            },
            block = getContentPreferenceUseCase::invoke
        )
    }

    override fun changeView(isGrid: Boolean) {
        updateState { it.copy(isGridSelected = isGrid) }
    }
}