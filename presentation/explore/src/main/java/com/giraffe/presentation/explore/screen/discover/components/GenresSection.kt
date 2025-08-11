package com.giraffe.presentation.explore.screen.discover.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.Chip
import com.giraffe.presentation.explore.model.GenreUi

@Composable
fun GenresSection(
    modifier: Modifier = Modifier,
    selectedGenre: GenreUi,
    genres: List<GenreUi>,
    onGenreSelected: (GenreUi) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(genres, key = { it.id }) { genre ->
            Chip(
                text = genre.title,
                isSelected = genre == selectedGenre,
                onCheckedChange = { onGenreSelected(genre) }
            )
        }
    }
}