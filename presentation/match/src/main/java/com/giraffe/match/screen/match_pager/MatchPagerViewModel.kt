package com.giraffe.match.screen.match_pager

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.giraffe.match.base.BaseViewModel
import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.usecase.GetMoviesGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchPagerViewModel @Inject constructor(
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<MatchScreenState, MatchScreenEffect>(MatchScreenState()) {

    init {
        loadGenres()
        restoreStateFromSavedState()
    }

    private fun restoreStateFromSavedState() {
        val savedMoodSelections = savedStateHandle.get<List<Int>>("moodSelections") ?: emptyList()
        val savedGenreSelections = savedStateHandle.get<List<Int>>("genreSelections") ?: emptyList()
        val savedTimeSelection = savedStateHandle.get<Int>("timeSelection")
        val savedReleasePeriodSelection = savedStateHandle.get<Int>("releasePeriodSelection")
        val savedCurrentPage = savedStateHandle.get<Int>("currentPage") ?: 0

        updateState { currentState ->
            currentState.copy(
                moodSelections = savedMoodSelections,
                genreSelections = savedGenreSelections,
                timeSelection = savedTimeSelection,
                releasePeriodSelection = savedReleasePeriodSelection,
                currentPage = savedCurrentPage
            )
        }
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

    fun onBackClicked() = safeExecute {
        val currentPage = state.value.currentPage
        if (currentPage > 0) {
            val newPage = currentPage - 1
            updateState { it.copy(currentPage = newPage) }
            savedStateHandle["currentPage"] = newPage
        } else {
            sendEffect(MatchScreenEffect.NavigateBack)
        }
    }


    fun onNextClicked() {
        viewModelScope.launch {
            if (state.value.currentPage < 4) {
                val newPage = state.value.currentPage + 1
                updateState { it.copy(currentPage = newPage) }
                savedStateHandle["currentPage"] = newPage
            } else {
                saveAllDataToSavedState()
                sendEffect(MatchScreenEffect.FinishMatching)
            }
        }
    }

    private fun saveAllDataToSavedState() {
        val currentState = state.value
        savedStateHandle["genreSelections"] = currentState.genreSelections
        savedStateHandle["moodSelections"] = currentState.moodSelections
        savedStateHandle["timeSelection"] = currentState.timeSelection
        savedStateHandle["releasePeriodSelection"] = currentState.releasePeriodSelection
        savedStateHandle["currentPage"] = currentState.currentPage
    }

    fun updateMoodSelections(selectedIds: List<Int>) {
        updateState { it.copy(moodSelections = selectedIds) }
        savedStateHandle["moodSelections"] = selectedIds
    }

    fun updateGenreSelections(selectedIds: List<Int>) {
        updateState { it.copy(genreSelections = selectedIds) }
        savedStateHandle["genreSelections"] = selectedIds
    }

    fun updateTimeSelection(selectedId: Int) {
        updateState { it.copy(timeSelection = selectedId) }
        savedStateHandle["timeSelection"] = selectedId
    }

    fun updateRecencySelection(selectedId: Int) {
        updateState { it.copy(releasePeriodSelection = selectedId) }
        savedStateHandle["releasePeriodSelection"] = selectedId
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