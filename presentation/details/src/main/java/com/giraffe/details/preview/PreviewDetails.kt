package com.giraffe.details.preview

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.components.AddToCollectionContent
import com.giraffe.details.components.CastCard
import com.giraffe.details.components.GallerySection
import com.giraffe.details.components.MainDetails
import com.giraffe.details.components.MainDetailsHeader
import com.giraffe.details.components.MainMovieOrSeriesDetails
import com.giraffe.details.components.MinimizedInfoRow
import com.giraffe.details.components.RatingSection
import com.giraffe.details.components.RatingSelector
import com.giraffe.details.components.ReviewCard
import com.giraffe.details.components.SeasonCard
import com.giraffe.details.components.StaffInfoSection
import com.giraffe.details.components.StaffMember
import com.giraffe.details.components.StarCastSection
import com.giraffe.details.components.sampleCastList
import com.giraffe.details.utils.imageSourceToPainter
import com.giraffe.details.utils.getCurrentLocalDate
import com.giraffe.details.utils.getCurrentLocalDateTime


@Composable
@Preview()
fun PreviewAddToCollectionContentLight() {
    CineVerseTheme(isDarkTheme = false) {
        AddToCollectionContent(
            title = "My Folder",
            isLoading = true,
            modifier = Modifier
                .width(304.dp)
                .height(48.dp)
        )
    }
}

@Composable
@Preview()
fun PreviewAddToCollectionContentDark() {
    CineVerseTheme(isDarkTheme = true) {
        AddToCollectionContent(
            title = "My Folder",
            isLoading = true,
            modifier = Modifier
                .width(304.dp)
                .height(48.dp)
        )
    }
}


@Preview
@Composable
fun GallerySectionPreview() {
    CineVerseTheme(isDarkTheme = true) {
        GallerySection(
            modifier = Modifier.height(314.dp),
            images = listOf(
                Pair(null, "gallery_image_one"),
                Pair(null, "gallery_image_two"),
                Pair(R.drawable.gallery_item3, "gallery_image_three"),
            ),
            onShowMoreClick = {}
        )
    }
}




@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview
fun MainDetailsPreview() {
    CineVerseTheme(isDarkTheme = true) {
        val scrollState = rememberScrollState()
        var isScroll by remember {
            mutableStateOf(false)
        }
        isScroll = scrollState.value > 5
        Box(
            modifier = Modifier
                .background(Theme.color.background.screen)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)

            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 252.dp)
                        .height(2000.dp)
                        .fillMaxWidth()
                        .background(Color.Red)
                )
            }
            SharedTransitionLayout {
                AnimatedContent(
                    isScroll,
                    transitionSpec = {
                        fadeIn(
                            animationSpec = tween(100)
                        ) togetherWith fadeOut(animationSpec = tween(100))
                    },
                    label = "Animated Content"
                ) { targetState ->
                    when (targetState) {
                        true -> MainDetailsHeader(
                            actorImage = R.drawable.gallery_item2.imageSourceToPainter(),
                            actorName = "Christian Bale",
                            animatedVisibilityScope = this@AnimatedContent,
                            sharedTransitionScope = this@SharedTransitionLayout,
                        )

                        false -> MainDetails(
                            modifier = Modifier.padding(top = 72.dp),
                            actorImage = R.drawable.gallery_item2.imageSourceToPainter(),
                            actorName = "Christian Bale",
                            actorBirthday = "Jan 30, 1974",
                            actorPlaceOfBirth = "Cardiff, Wales, UK",
                            animatedVisibilityScope = this@AnimatedContent,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            onYoutubeClick = {},
                            onFacebookClick = {},
                            onInstagramClick = {}
                        )
                    }
                }
            }
            AppBar(
                showBackButton = true,
                hasBackground = false,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
@Preview
fun PreviewMainMovieDetails() {
    CineVerseTheme (
        isDarkTheme = true
    ) {
        MainMovieOrSeriesDetails(
            modifier = Modifier.width(360.dp),
            poster = com.giraffe.designsystem.R.drawable.main_poster_test.imageSourceToPainter(),
            name = "The Dark Knight",
            genres = listOf("Drama", "Action", "Crime", "Thriller", "Drama", "Action", "Crime"),
            rating = 8.5f,
            duration = "2h 32m",
            releaseDate = "2008, Jul 18",
            type = "Movie"
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    backgroundColor = 0xFF121321
)
fun PreviewMinimizedInfoRow() {
    CineVerseTheme(isDarkTheme = true) {
        MinimizedInfoRow(
            poster = com.giraffe.designsystem.R.drawable.main_poster_test.imageSourceToPainter(),
            title = "The Dark Knight",
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
}

@Composable
@Preview
fun PreviewRatingSection() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        RatingSection(
            modifier = Modifier
        )
    }
}

@Composable
@Preview
fun PreviewRatingSectionDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        RatingSection(
            modifier = Modifier
        )
    }
}



@Composable
@Preview
fun PreviewRatingSelector() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        RatingSelector(
            modifier = Modifier
        )
    }
}

@Composable
@Preview
fun PreviewRatingSelectorDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        RatingSelector(
            modifier = Modifier
        )
    }
}


@Composable
@Preview
fun PreviewReviewCard() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        ReviewCard(
            rate = 3,
            reviewText = "While we were focusing on some videos due to the need of the tasks, we still need to cover some important topics in compose now. without restriction to finish it at the first day of the week.\n" +
                    "watch the following videos from compose playlist on YouTube",
            reviewerName = "Bilal Azzam",
            reviewerUsername = "bilal_azzam",
            reviewDate = getCurrentLocalDateTime(),
            reviewerImageSource = ""
        )
    }
}

@Composable
@Preview
fun PreviewReviewCardDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        ReviewCard(
            rate = 3,
            reviewText = "While we were focusing on some videos due to the need of the tasks, we still need to cover some important topics in compose now. without restriction to finish it at the first day of the week.\n" +
                    "watch the following videos from compose playlist on YouTube",
            reviewerName = "Bilal Azzam",
            reviewerUsername = "bilal_azzam",
            reviewDate = getCurrentLocalDateTime(),
            reviewerImageSource = ""
        )
    }
}


@Preview(
    name = "SeasonCard Dark",
    apiLevel = 34,
)
@Composable
fun PreviewSeasonCardDark() {
    CineVerseTheme(isDarkTheme = true) {
        SeasonCard(
            poster = R.drawable.gallery_item.imageSourceToPainter(),
            title = "Season",
            caption = "Caption",
            rating = 7.5,
            episodes = 20,
            year = 2019,
            onClick = {}
        )
    }
}

@Preview(
    name = "SeasonCard Light",
    apiLevel = 34,
)
@Composable
fun PreviewSeasonCardLight() {
    CineVerseTheme(isDarkTheme = false) {
        SeasonCard(
        poster = R.drawable.gallery_item.imageSourceToPainter(),
            title = "Season",
            caption = "Caption",
            rating = 7.5,
            episodes = 20,
            year = 2019,
            onClick = {}
        )
    }
}

@Preview(
    name = "StaffInfoSection Preview - Dark",
    apiLevel = 34
)
@Composable
fun PreviewStaffInfoSectionDark() {
    CineVerseTheme(isDarkTheme = true) {
        StaffInfoSection(
            title = "Staff Info",
            staffList = listOf(
                StaffMember(name = "John Doe", role = "Director"),
                StaffMember(name = "Christopher Nolan", role = "Director, Screenplay, Story"),
                StaffMember(name = "Mike Johnson", role = "Writer")
            )
        )
    }
}

@Preview(
    name = "StaffInfoSection Preview - Light",
    showBackground = true,
    apiLevel = 34
)
@Composable
fun PreviewStaffInfoSectionLight() {
    CineVerseTheme(isDarkTheme = false) {
        StaffInfoSection(
            title = "Staff Info",
            staffList = listOf(
                StaffMember(name = "John Doe", role = "Director"),
                StaffMember(name = "Christopher Nolan", role = "Director, Screenplay, Story"),
                StaffMember(name = "Mike Johnson", role = "Writer")
            )
        )
    }
}

@Composable
@Preview(
    name = "StarCastSection Light",
    apiLevel = 34
)
fun PreviewStarCastSectionLight() {
    CineVerseTheme(isDarkTheme = false) {
        StarCastSection(
            title = "Star Cast",
            onShowMoreClick = {},
            castList = sampleCastList()
        )
    }
}

@Composable
@Preview(
    name = "StarCastSection Dark",
    apiLevel = 34
)
fun PreviewStarCastSectionDark() {
    CineVerseTheme(isDarkTheme = true) {
        StarCastSection(
            title = "Star Cast",
            onShowMoreClick = {},
            castList = sampleCastList()
        )
    }
}
@Composable
@Preview(
    name = "CastCard Preview",
    apiLevel = 34
)
fun PreviewCastCard() {
    CineVerseTheme(isDarkTheme = false) {
        CastCard(
            actorName = "Robert Downey.",
            character = "Iron Man",
            actorImage = painterResource(
                id = com.giraffe.designsystem.R.drawable.reviewer
            )
        )
    }
}
@Composable
@Preview(
    name = "CastCard Preview Dark",
    apiLevel = 34
)
fun PreviewCastCardDark() {
    CineVerseTheme(isDarkTheme = true) {
        CastCard(
            actorName = "Robert Downey.",
            character = "Iron Man",
            actorImage = painterResource(
                id = com.giraffe.designsystem.R.drawable.reviewer
            )
        )
    }
}