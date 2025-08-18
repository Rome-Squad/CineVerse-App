package com.giraffe.presentation.explore.screen.discover.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.explore.components.TransitionLazyColumnToGrid
import com.giraffe.presentation.explore.components.uimodel.Poster
import com.giraffe.presentation.explore.model.GenreUi

@Composable
fun GenresAndCardsSection(
    modifier: Modifier = Modifier,
    posters: LazyPagingItems<Poster>,
    selectedGenre: GenreUi,
    genres: List<GenreUi>,
    isGridSelected: Boolean,
    onGenreSelected: (GenreUi) -> Unit,
    onPosterClicked: (Int) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.background.screen)
    ) {
        TransitionLazyColumnToGrid(
            posters = posters,
            onPosterClicked = onPosterClicked,
            isListSelected = !isGridSelected,
            contentPadding = PaddingValues(vertical = 60.dp, horizontal = 16.dp),
        )
        GenresSection(
            modifier = Modifier.padding(top = 12.dp, bottom = 16.dp),
            genres = genres,
            selectedGenre = selectedGenre,
            onGenreSelected = onGenreSelected
        )
    }
}
