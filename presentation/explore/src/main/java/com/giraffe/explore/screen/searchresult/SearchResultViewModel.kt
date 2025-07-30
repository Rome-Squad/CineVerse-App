package com.giraffe.explore.screen.searchresult

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.giraffe.explore.base.BaseViewModel
import com.giraffe.explore.screen.discover.SearchTab
import com.giraffe.explore.util.BasePagingSource
import com.giraffe.explore.util.toPoster
import com.giraffe.explore.util.toUi
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.media.movies.usecase.SearchMovieByNameUseCase
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.usecase.SearchPeopleByNameUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import com.giraffe.media.series.usecase.SearchSeriesByNameUseCase
import com.giraffe.media.util.NetworkInterceptor
import kotlinx.coroutines.flow.map

class SearchResultViewModel(
    private val query: String,
    private val searchMovieByName: SearchMovieByNameUseCase,
    private val searchSeriesByName: SearchSeriesByNameUseCase,
    private val searchPeopleByName: SearchPeopleByNameUseCase,
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresUseCase,
    ) : BaseViewModel<SearchResultScreenState>(SearchResultScreenState(query = query)),
    SearchResultInteractionListener {
    init {

        getMoviesGenres()
        getSeriesGenres()
        getMovies()
        getSeries()
        getActors()
    }



            @SuppressLint("StateFlowValueCalledInComposition")
    override fun selectTap(tabIndex: Int) {
        safeExecute {
            updateState { it.copy(selectedTab = SearchTab.entries[tabIndex]) }
            when (state.value.selectedTab) {
                SearchTab.MOVIES -> updateState { it.copy(selectedPosters = state.value.moviesPosters) }
                SearchTab.SERIES -> updateState { it.copy(selectedPosters = state.value.seriesPosters) }
                SearchTab.ACTORS -> {}
            }
        }
    }

    override fun changeView(isGrid: Boolean) {
        safeExecute { updateState { it.copy(isGridSelected = isGrid) } }
    }

    private fun getMoviesGenres() {
        safeExecute {
            getMoviesGenresUseCase().let { genres ->
                updateState { it.copy(moviesGenres = genres.map { genre -> genre.toUi() }) }
            }
        }
    }

    private fun getSeriesGenres() {
        safeExecute {
            getSeriesGenresUseCase().let { genres ->
                updateState { it.copy(seriesGenres = genres.map { genre -> genre.toUi() }) }
            }
        }
    }

    private fun getMovies() {
        safeExecute {
            val moviesFlow =
                Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                    BasePagingSource { page -> searchMovieByName(query, page) }
                }.flow.cachedIn(viewModelScope).map { it.map(Movie::toPoster) }
            updateState { it.copy(selectedPosters = moviesFlow, moviesPosters = moviesFlow) }
        }
    }

    private fun getSeries() {
        safeExecute {
            val seriesFlow =
                Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                    BasePagingSource { page -> searchSeriesByName(query, page) }
                }.flow.cachedIn(viewModelScope).map { it.map(Series::toPoster) }
            updateState { it.copy(seriesPosters = seriesFlow) }
        }
    }

    private fun getActors() {
        safeExecute {
            if (!isConnect.value) {
                forceUpdateNetworkState(false)
                return@safeExecute
            }
            val actorsFlow =
                Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                    BasePagingSource { page -> searchPeopleByName(query, page) }
                }.flow.cachedIn(viewModelScope).map { it.map(Person::toUi) }

             updateState { it.copy(actors = actorsFlow) }

        }
}



}
