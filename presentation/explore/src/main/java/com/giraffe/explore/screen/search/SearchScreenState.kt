package com.giraffe.explore.screen.search

import androidx.annotation.StringRes
import com.giraffe.explore.screen.discover.DiscoverScreenState
import com.giraffe.media.explore.util.HasErrorMessage

data class SearchScreenState(
    val query: String = "",
    val isSearchFieldFocused: Boolean = false,
    val errorMessageRes: Int = 0,
    val postfixIconRes: Int = 0
) : HasErrorMessage<SearchScreenState> {
    override fun withErrorMessage(@StringRes id: Int) = copy(errorMessageRes = id)
}