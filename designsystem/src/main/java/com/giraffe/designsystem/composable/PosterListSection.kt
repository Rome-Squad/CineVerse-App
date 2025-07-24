package com.giraffe.designsystem.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.uimodel.Poster

@Composable
fun PosterListSection(
    title: String,
    poster: List<Poster>,
    modifier: Modifier = Modifier,
    endText: String? = null,
    paddingHorizontal: Int = 16,
    onClickEndText: () -> Unit = {},
    onClickPoster: (movieId: Int) -> Unit = {}
) {
    if (poster.isEmpty()) {
        return
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        SectionTitle(
            modifier = Modifier
                .padding(horizontal = paddingHorizontal.dp),
            title = title,
            clickableText = endText,
            onClickableText = onClickEndText
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = paddingHorizontal.dp)
        ) {
            items(poster) { movie ->
                PosterItemVertically(
                    movie = movie,
                    modifier = Modifier
                        .width(136.dp),
                    onClickPoster = { onClickPoster(movie.id) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CineVerseTheme(isDarkTheme = true) {
        Column(
            verticalArrangement = Arrangement.spacedBy(80.dp)
        ) {
            PosterListSection(
                modifier = Modifier.fillMaxWidth(),
                title = "Popular Movies",
                endText = "Show More",
                poster = listOf(
                    Poster(
                        id = 1,
                        name = "The FlashThe FlashThe FlashThe FlashThe FlashThe FlashThe FlashThe FlashThe Flash",
                        imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                        rating = 7.5f,
                    ),
                    Poster(
                        id = 2,
                        name = "******The Flash",
                        imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                        rating = 7.5f,
                    ),
                    Poster(
                        id = 3,
                        name = "The Flash",
                        imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                        rating = 7.5f,
                    ),
                    Poster(
                        id = 4,
                        name = "The Flash",
                        imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                        rating = 7.5f,
                    ),
                ),
                onClickEndText = {},
                onClickPoster = {}
            )

            PosterListSection(
                modifier = Modifier.fillMaxWidth(),
                title = "Popular Movies",
                poster = listOf(
                    Poster(
                        id = 1,
                        name = "The Flash",
                        imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                        rating = 7.5f,
                    ),
                    Poster(
                        id = 2,
                        name = "The Flash",
                        imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                        rating = 7.5f,
                    ),
                    Poster(
                        id = 3,
                        name = "The Flash",
                        imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                        rating = 7.5f,
                    ),
                    Poster(
                        id = 4,
                        name = "The Flash",
                        imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                        rating = 7.5f,
                    ),
                ),
                onClickPoster = {}
            )
        }
    }
}