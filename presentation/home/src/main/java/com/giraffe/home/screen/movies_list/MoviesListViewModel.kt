package com.giraffe.home.screen.movies_list

import com.giraffe.home.base.BaseViewModel

class MoviesListViewModel() :
    BaseViewModel<MoviesListUiState, MoviesListEffect>(initialState = MoviesListUiState()),
    MoviesListInteractionListener {

    override fun onViewChanged(isGrid: Boolean) {
        updateState {
            it.copy(isListSelected = !isGrid)
        }
    }
}