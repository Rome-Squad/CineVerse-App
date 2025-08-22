package com.giraffe.presentation.details.screens.castCredit

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.media.entity.Genre
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.usecase.GetCastCreditsUseCase
import com.giraffe.media.movie.usecase.genre.ObserveMoviesGenresUseCase
import com.giraffe.media.series.usecase.genre.ObserveSeriesGenresUseCase
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.components.uimodel.Poster
import com.giraffe.presentation.details.navigation.routes.CastCreditRoute
import com.giraffe.presentation.details.utils.toPoster
import com.giraffe.presentation.details.utils.toUi
import com.giraffe.user.usecase.GetContentPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
        observeContentPreference()
        loadCastCredit(state.value.castId)
    }

    private fun loadCastCredit(castId: Int) {
        updateState { it.copy(isNoInternet = false, isLoading = true) }

        safeExecute(
            onSuccess = ::loadCastCreditSuccess,
            onError = ::onError,
            block = { getPeopleMediaCredits(castId) }
        )
    }

    private fun loadCastCreditSuccess(castCredits: MediaMemberRepository.CastMedia) {
        safeCollect(
            onEmitNewValue = { onEmitMovies(castCredits, it) },
            onError = ::onError
        ) {
            observeMoviesGenresUseCase()
        }

        safeCollect(
            onEmitNewValue = { onEmitSeries(castCredits, it) },
            onError = ::onError
        ) {
            observeSeriesGenresUseCase()
        }

        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = false
            )
        }
    }

    private fun onEmitSeries(
        castCredits: MediaMemberRepository.CastMedia,
        genres: List<Genre>
    ) {
        val seriesPosters = castCredits.series.map { series ->
            series.toUi(genres.filter { it.id in series.genreIDs })
                .toPoster()
        }
        updateState { it.copy(seriesPosters = seriesPosters) }
    }

    private fun onEmitMovies(castCredits: MediaMemberRepository.CastMedia, genres: List<Genre>) {
        val moviesPosters = castCredits.movies.map { movie ->
            movie.toUi(genres.filter { it.id in movie.genresID }).toPoster()
        }
        updateState { it.copy(moviesPosters = moviesPosters) }
    }

    private fun onError(exception: Throwable) {
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

    override fun onRetryClick() {
        loadCastCredit(state.value.castId)
    }
}