package com.giraffe.details.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.MoviesListSection
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.components.MainMovieOrSeriesDetails
import com.giraffe.details.components.RatingSection
import com.giraffe.details.components.ReviewCard
import com.giraffe.details.components.StaffInfoSection
import com.giraffe.details.components.StaffMember
import com.giraffe.details.components.StarCastSection
import com.giraffe.details.components.sampleCastList
import com.giraffe.details.utils.getCurrentLocalDate
import com.giraffe.details.utils.imageSourceToPainter

val movies = listOf(
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
    )
)

@Composable
fun MovieDetails(
    modifier: Modifier = Modifier
) {

    MovieDetailsContent(
        modifier = modifier,
        onShowMoreReviewsClick = {}
    )
}

@Composable
private fun MovieDetailsContent(
    modifier: Modifier,
    onShowMoreReviewsClick : ()->Unit
) {

    LazyColumn (
        modifier = modifier.background(Theme.color.background.screen).padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        item {
            AppBar(
                showBackButton = true, modifier = Modifier.padding(16.dp)
            )
        }
        item {
            MainMovieOrSeriesDetails(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                poster = R.drawable.main_poster_test.imageSourceToPainter(),
                name = "The Dark Knight",
                genres = listOf("Drama", "Action", "Thriller", "Drama", "Action", "Crime"),
                rating = 5.5f,
                duration = "2h 32m",
                releaseDate = "2008, Jul 18",
                type = "Movie"
            )
        }
        item {

            InfoSection(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                title = "Storyline",
                description = "Batman raises the stakes in his war on crime. With the help of Lt. Jim Gordon and District Attorney Harvey Dent, Batman sets out to dismantle the remaining criminal organizations that plague the streets. The partnership proves to be effective, but they soon find themselves prey to a reign of chaos unleashed by a rising criminal mastermind known to the terrified citizens of Gotham as the Joker.",
            )
        }
        item {
                StarCastSection(
                    title = "Star Cast",
                    onShowMoreClick = {},
                    castList = sampleCastList(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        item {
            StaffInfoSection(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                title = "Behind the Scenes",
                onShowMoreClick = {},
                staffList = listOf(
                    StaffMember(name = "John Doe", role = "Director"),
                    StaffMember(name = "Christopher Nolan", role = "Director, Screenplay, Story"),
                    StaffMember(name = "Mike Johnson", role = "Writer")
                )
            )
        }
        item {
            MoviesListSection(
                title = "You Might Also Like",
                endText = "Show More",
                movies = listOf(
                    Poster(
                        id = 1,
                        name = "The Flash",
                        imageUri = "",
                        rating = 7.5f,
                    ),
                    Poster(
                        id = 2,
                        name = "The Flash",
                        imageUri = "",
                        rating = 7.5f,
                    ),
                    Poster(
                        id = 3,
                        name = "The Flash",
                        imageUri = "",
                        rating = 7.5f,
                    ),
                    Poster(
                        id = 4,
                        name = "The Flash",
                        imageUri = "",
                        rating = 7.5f,
                    ),
                ),
                onClickEndText = {},
                onClickPoster = {}
            )
        }

        item {
            RatingSection(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
            )
        }

        item{
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Top Reviews",
                    color = Theme.color.shade.primary,
                    style = Theme.textStyle.title.sm,
                )

                Text(
                    text = stringResource(com.giraffe.details.R.string.show_more),
                    color = Theme.color.brand.primary,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .clickable { onShowMoreReviewsClick() },
                    style = Theme.textStyle.body.md.medium
                )
            }
        }

        item{
            ReviewCard(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                rate = 5,
                reviewText = "very good",
                reviewDate = getCurrentLocalDate(),
                reviewerImageSource = R.drawable.reviewer,
                reviewerName = "Hend",
                reviewerUsername = "Hend sayed"
            )
        }
        item{
            ReviewCard(
                modifier = Modifier.padding(horizontal = 16.dp),
                rate = 5,
                reviewText = "very good",
                reviewDate = getCurrentLocalDate(),
                reviewerImageSource = R.drawable.reviewer,
                reviewerName = "Hend",
                reviewerUsername = "Hend sayed"
            )
        }
        item{
            ReviewCard(
                modifier = Modifier.padding(top=12.dp, start = 16.dp,end=16.dp),
                rate = 5,
                reviewText = "very good",
                reviewDate = getCurrentLocalDate(),
                reviewerImageSource = R.drawable.reviewer,
                reviewerName = "Hend",
                reviewerUsername = "Hend sayed"
            )
        }
    }
}

@Preview
@Composable
private fun MovieDetailsPreview() {
    CineVerseTheme(
        isDarkTheme = false
    ) {
        MovieDetails()
    }
}