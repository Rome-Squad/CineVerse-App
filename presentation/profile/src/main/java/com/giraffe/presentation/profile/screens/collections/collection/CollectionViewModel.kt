package com.giraffe.presentation.profile.screens.collections.collection

import androidx.lifecycle.SavedStateHandle
import com.giraffe.media.collections.usecase.GetCollectionMoviesUseCase
import com.giraffe.media.collections.usecase.RemoveMovieFromCollectionUseCase
import com.giraffe.media.entity.Genre
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.presentation.profile.base.BaseViewModel
import com.giraffe.presentation.profile.model.toSwipeablePoster
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val getCollectionMoviesUseCase: GetCollectionMoviesUseCase,
    private val removeMovieFromCollectionUseCase: RemoveMovieFromCollectionUseCase,
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    savedStateHandle: SavedStateHandle

) : BaseViewModel<CollectionScreenState, CollectionEffect>(
    CollectionScreenState()
), CollectionInteractionListener {

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
            onError = ::onGetMoviesGenresFailure
        ) {
            getMoviesGenresUseCase()
        }
    }

    private fun onGetMoviesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                isLoading = false,
                movieGenres = genres
            )
        }
    }

    private fun onGetMoviesGenresFailure(error: Throwable) {
        updateState {
            it.copy(
                isLoading = false
            )
        }

        sendEffect(CollectionEffect.ShowError(mapErrorToResource(error)))
    }
    private fun getCollectionItems(
    ) {
        safeExecute(
            onSuccess = ::onGetCollectionItemsSuccess,
            onError = ::onGetCollectionItemsFailure
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

    private fun onGetCollectionItemsFailure(error: Throwable) {
        updateState {
            it.copy(
                isLoading = false
            )
        }

        sendEffect(CollectionEffect.ShowError(mapErrorToResource(error)))
    }

    override fun onBackClick() {
        sendEffect(CollectionEffect.NavigateBack)
    }

    override fun onPosterClick(id: Int) {
        sendEffect(CollectionEffect.NavigateToMovieDetails(id))
    }

    override fun onDeletePosterClick(id: Int) {
        safeExecute(
            onSuccess = ::onRemoveMovieFromCollectionSuccess,
            onError = ::onRemoveMovieFromCollectionFailure
        ) {
            removeMovieFromCollectionUseCase(
                collectionId = state.value.collectionId,
                movieId = id
            )
        }
    }

    private fun onRemoveMovieFromCollectionSuccess(isRemoved: Boolean) {
        if (isRemoved)
            getCollectionItems()
    }

    private fun onRemoveMovieFromCollectionFailure(error: Throwable) {
        sendEffect(CollectionEffect.ShowError(mapErrorToResource(error)))
    }

    override fun onCloseTipClick() {
        updateState {
            it.copy(
                isDeleteTipVisible = false
            )
        }
    }

}