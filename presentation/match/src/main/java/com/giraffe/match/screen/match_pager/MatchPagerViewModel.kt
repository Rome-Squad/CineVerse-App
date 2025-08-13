package com.giraffe.match.screen.match_pager

import androidx.lifecycle.viewModelScope
import com.giraffe.match.base.BaseViewModel
import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.usecase.GetMoviesGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchPagerViewModel @Inject constructor(
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase
) : BaseViewModel<MatchScreenState, MatchScreenEffect>(MatchScreenState()) {

    init {
        loadGenres()
    }

    private fun loadGenres() {
        safeExecute(
            onSuccess = ::onGetMoviesGenresSuccess,
            onError = ::onFailure,
            block = getMoviesGenresUseCase::invoke
        )
    }

    private fun onFailure(error: Throwable, isNoInternet: Boolean) {
        updateState { it.copy(isNoInternet = isNoInternet) }
        sendEffect(MatchScreenEffect.ShowError(error))
    }

    private fun onGetMoviesGenresSuccess(genres: List<Genre>) {
        val genreOptions = genres.map { genre ->
            SelectionOption(
                id = genre.id,
                label = genre.title
            )
        }
        updateState { it.copy(genreOptions = genreOptions) }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            if (_state.value.currentPage > 0) {
                updateState { it.copy(currentPage = it.currentPage - 1) }
            } else {
                sendEffect(MatchScreenEffect.NavigateBack)
            }
        }
    }

    fun onNextClicked() {
        viewModelScope.launch {
            if (_state.value.currentPage < 4) {
                updateState { it.copy(currentPage = it.currentPage + 1) }
            } else {
                sendEffect(MatchScreenEffect.FinishMatching)
            }
        }
    }

    fun updateMoodSelections(selectedIds: List<Int>) {
        updateState { it.copy(moodSelections = selectedIds) }
    }

    fun updateGenreSelections(selectedIds: List<Int>) {
        updateState { it.copy(genreSelections = selectedIds) }
    }

    fun updateTimeSelection(selectedId: Int) {
        updateState { it.copy(timeSelection = selectedId) }
    }

    fun updateRecencySelection(selectedId: Int) {
        updateState { it.copy(releasePeriodSelection = selectedId) }
    }

    fun updateLoadingState(isLoading: Boolean) {
        updateState { it.copy(isLoading = isLoading) }
    }

    fun isPageEnabled(state: MatchScreenState): Boolean {
        return when (state.currentPage) {
            0 -> state.moodSelections.isNotEmpty()
            1 -> state.genreSelections.isNotEmpty()
            2 -> state.timeSelection != null
            3 -> state.releasePeriodSelection != null
            else -> true
        }
    }
}