package com.giraffe.presentation.profile.screens.collectiondetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.media.collections.usecase.GetCollectionMoviesUseCase
import com.giraffe.media.collections.usecase.RemoveMovieFromCollectionUseCase
import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.genre.ObserveMoviesGenresUseCase
import com.giraffe.presentation.profile.base.BaseViewModel
import com.giraffe.presentation.profile.navigation.routes.CollectionRoute
import com.giraffe.presentation.profile.utils.toSwipeablePoster
import com.giraffe.user.usecase.GetContentPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectionDetailsViewModel @Inject constructor(
    private val getCollectionMoviesUseCase: GetCollectionMoviesUseCase,
    private val removeMovieFromCollectionUseCase: RemoveMovieFromCollectionUseCase,
    private val observeMoviesGenresUseCase: ObserveMoviesGenresUseCase,
    private val getContentPreferenceUseCase: GetContentPreferenceUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CollectionDetailsScreenState, CollectionDetailsEffect>(
    CollectionDetailsScreenState(
        collectionId = savedStateHandle.toRoute<CollectionRoute>().collectionId,
        collectionName = savedStateHandle.toRoute<CollectionRoute>().collectionName,
    )
), CollectionDetailsInteractionListener {

    init {
        observeContentPreference()
        getMoviesGenres()
    }

    private fun getMoviesGenres() {
        safeCollect(
            onEmitNewValue = ::onGetMoviesGenresSuccess,
            onError = ::onFailure.also { getCollectionItems() },
            block = observeMoviesGenresUseCase::invoke
        )
    }

    private fun onGetMoviesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                isNoInternet = false,
                movieGenres = genres
            )
        }
        getCollectionItems()
    }

    private fun onFailure(error: Throwable, isNoInternet: Boolean) {
        updateState { it.copy(isLoading = false, isNoInternet = isNoInternet) }
        sendEffect(CollectionDetailsEffect.ShowError(error))
    }

    private fun getCollectionItems() {
        safeExecute(
            onSuccess = ::onGetCollectionItemsSuccess,
            onError = ::onFailure
        ) {
            getCollectionMoviesUseCase(
                collectionId = state.value.collectionId
            )
        }
    }

    private fun onGetCollectionItemsSuccess(moviesList: List<Movie>) {
        val swipeablePosters = moviesList.map { movie ->
            movie.toSwipeablePoster(
                genres = state.value.movieGenres
            )
        }
        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = false,
                collectionMovies = swipeablePosters
            )
        }
    }

    override fun onBackClick() {
        sendEffect(CollectionDetailsEffect.NavigateBack)
    }

    override fun onPosterClick(id: Int) {
        sendEffect(CollectionDetailsEffect.NavigateToMovieDetails(id))
    }

    override fun onDeletePosterClick(id: Int) {
        safeExecute(
            onSuccess = ::onRemoveMovieFromCollectionSuccess,
            onError = ::onFailure
        ) {
            removeMovieFromCollectionUseCase(
                collectionId = state.value.collectionId,
                movieId = id
            )
        }
    }

    private fun onRemoveMovieFromCollectionSuccess(isRemoved: Boolean) {
        if (isRemoved) getCollectionItems()
    }

    override fun onCloseTipClick() {
        updateState {
            it.copy(
                isDeleteTipVisible = false
            )
        }
    }

    override fun onStartCollectingClick() {
        sendEffect(CollectionDetailsEffect.NavigateToExplore)
    }

    private fun observeContentPreference() {
        safeCollect(
            onEmitNewValue = { preference ->
                updateState { it.copy(contentPreference = preference) }
            },
            block = getContentPreferenceUseCase::invoke
        )
    }
}