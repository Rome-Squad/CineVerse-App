package com.giraffe.presentation.profile.screens.collectiondetails

import androidx.lifecycle.SavedStateHandle
import com.giraffe.media.collections.usecase.GetCollectionMoviesUseCase
import com.giraffe.media.collections.usecase.RemoveMovieFromCollectionUseCase
import com.giraffe.media.entity.Genre
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.presentation.profile.base.BaseViewModel
import com.giraffe.presentation.profile.utils.toSwipeablePoster
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectionDetailsViewModel @Inject constructor(
    private val getCollectionMoviesUseCase: GetCollectionMoviesUseCase,
    private val removeMovieFromCollectionUseCase: RemoveMovieFromCollectionUseCase,
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    savedStateHandle: SavedStateHandle

) : BaseViewModel<CollectionDetailsScreenState, CollectionDetailsEffect>(
    CollectionDetailsScreenState()
), CollectionDetailsInteractionListener {

    init {
        savedStateHandle.get<String>("collectionName")?.let { collectionName ->
            updateState {
                it.copy(
                    collectionName = collectionName
                )
            }
        }

        savedStateHandle.get<Int>("collectionId")?.let { collectionId ->
            updateState {
                it.copy(
                    collectionId = collectionId
                )
            }
        }

        getMoviesGenres()
        getCollectionItems()
    }

    private fun getMoviesGenres() {
        safeExecute(
            onSuccess = ::onGetMoviesGenresSuccess,
            onError = ::onFailure
        ) {
            getMoviesGenresUseCase()
        }
    }

    private fun onGetMoviesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = false,
                movieGenres = genres
            )
        }
    }

    private fun onFailure(error: Throwable) {
        updateState { it.copy(isLoading = false, isNoInternet = error is NoInternetException) }
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
}