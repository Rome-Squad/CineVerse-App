package com.giraffe.presentation.details.screens.castCredit

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.usecase.GetCastCreditsUseCase
import com.giraffe.media.movie.usecase.genre.GetMoviesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.components.uimodel.Poster
import com.giraffe.presentation.details.navigation.routes.CastCreditRoute
import com.giraffe.presentation.details.utils.toPoster
import com.giraffe.presentation.details.utils.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.giraffe.user.exception.NoInternetException as UserNoInternetException

@HiltViewModel
class CastCreditViewModel @Inject constructor(
    private val getPeopleMediaCredits: GetCastCreditsUseCase,
    private val getSeriesGenresByIds: GetSeriesGenresByIdsUseCase,
    private val getMoviesGenresByIds: GetMoviesGenresByIdsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CastCreditScreenState, CastCreditEffect>(
    CastCreditScreenState(
        castId = savedStateHandle.toRoute<CastCreditRoute>().castID,
        actorName = savedStateHandle.toRoute<CastCreditRoute>().actorName
    )
), CastCreditInteractionListener {

    init {
        state.value.castId?.let {
            loadCastCredit(it)
        }
    }

    private fun loadCastCredit(castId: Int) {
        updateState { it.copy(isLoading = true) }

        safeExecute(
            onSuccess = ::loadCastCreditSuccess,
            onError = ::loadCastCreditError,
            block = { getPeopleMediaCredits(castId) }
        )
    }

    private fun loadCastCreditSuccess(castCredits: MediaMemberRepository.CastMedia) {
        safeExecute(
            onSuccess = ::updateCastCreditPosters,
            onError = ::loadCastCreditError
        ) {
            val seriesPosters = castCredits.series.map {
                val genres = getSeriesGenresByIds(it.genreIDs).map { genre -> genre.title }
                it.toUi().toPoster().copy(genres = genres.joinToString(", "))
            }

            val moviesPosters = castCredits.movies.map {
                val genres = getMoviesGenresByIds(it.genresID).map { genre -> genre.title }
                it.toUi().toPoster().copy(genres = genres.joinToString(", "))
            }

            seriesPosters + moviesPosters
        }
    }

    private fun updateCastCreditPosters(posters: List<Poster>) {
        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = false,
                posters = posters,
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

    override fun changeView(isGrid: Boolean) {
        updateState { it.copy(isGridSelected = isGrid) }
    }

    override fun onRetryClick() {
        state.value.castId?.let {
            loadCastCredit(it)
        }
    }
}