package com.giraffe.match.screen.match_pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchPagerViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(MatchScreenState())
    val state: StateFlow<MatchScreenState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MatchScreenEffect>()
    val effect = _effect.asSharedFlow()

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

    fun updateTimeSelections(selectedIds: List<Int>) {
        _state.value = _state.value.copy(timeSelections = selectedIds)
    }

    fun updateRecencySelections(selectedIds: List<Int>) {
        _state.value = _state.value.copy(recencySelections = selectedIds)
    }
}