package com.giraffe.match.screen.match_pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.media.entity.Genre
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.movie.usecase.GetMoviesGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchPagerViewModel @Inject constructor(
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase
) : ViewModel() {

    init {
        loadGenres()
    }

    private val _state = MutableStateFlow(MatchScreenState())
    val state: StateFlow<MatchScreenState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MatchScreenEffect>()
    val effect = _effect.asSharedFlow()

    private fun loadGenres() {
        safeExecute(
            onSuccess = ::onGetMoviesGenresSuccess,
            onError = ::onFailure,
            block = getMoviesGenresUseCase::invoke
        )
    }
    private suspend fun onFailure(error: Throwable, isNoInternet: Boolean) {
        _state.value = _state.value.copy(isNoInternet = isNoInternet)
        _effect.emit(MatchScreenEffect.ShowError(error))
    }

    private fun onGetMoviesGenresSuccess(genres: List<Genre>) {
        val genreOptions = genres.map { genre ->
            SelectionOption(
                id = genre.id,
                label = genre.title
            )
        }
        _state.value = _state.value.copy(genreOptions = genreOptions)

    }

    fun onBackClicked() {
        viewModelScope.launch {
            if (_state.value.currentPage > 0) {
                _state.value = _state.value.copy(currentPage = _state.value.currentPage - 1)
            } else {
                _effect.emit(MatchScreenEffect.NavigateBack)
            }
        }
    }

    fun onNextClicked() {
        viewModelScope.launch {
            if (_state.value.currentPage < 4) {
                _state.value = _state.value.copy(currentPage = _state.value.currentPage + 1)
            } else {
                _effect.emit(MatchScreenEffect.FinishMatching)
            }
        }
    }

    fun updateMoodSelections(selectedIds: List<Int>) {
        _state.value = _state.value.copy(moodSelections = selectedIds)
    }

    fun updateGenreSelections(selectedIds: List<Int>) {
        _state.value = _state.value.copy(genreSelections = selectedIds)
    }

    fun updateTimeSelection(selectedId: Int) {
        _state.value = _state.value.copy(timeSelection = selectedId)
    }

    fun updateRecencySelection(selectedId: Int) {
        _state.value = _state.value.copy(recencySelection = selectedId)
    }

    fun updateLoadingState(isLoading: Boolean) {
        _state.value = _state.value.copy(isLoading = isLoading)
    }
     fun isPageEnabled(state: MatchScreenState): Boolean {
        return when (state.currentPage) {
            0 -> state.moodSelections.isNotEmpty()
            1 -> state.genreSelections.isNotEmpty()
            2 -> state.timeSelection != null
            3 -> state.recencySelection != null
            else -> true
        }
    }
    private fun <T> safeExecute(
        onError: suspend (Throwable, Boolean) -> Unit = { _, _ -> },
        onSuccess: (T) -> Unit = {},
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> T
    ) {
        coroutineScope.launch(dispatcher) {
            runCatching {
                onSuccess(block())
            }.onFailure {
                onError(it, it is NoInternetException)
            }
        }
    }
}