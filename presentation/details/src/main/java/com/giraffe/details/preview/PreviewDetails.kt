package com.giraffe.details.preview

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.details.R
import com.giraffe.details.components.AddToCollectionContent
import com.giraffe.details.components.CastCard
import com.giraffe.details.components.GallerySection
import com.giraffe.details.components.MainDetails
import com.giraffe.details.components.MainMovieDetails
import com.giraffe.details.components.RatingSection
import com.giraffe.details.components.RatingSelector
import com.giraffe.details.components.ReviewCard
import com.giraffe.details.components.SeasonCard
import com.giraffe.details.components.StaffInfoSection
import com.giraffe.details.components.StaffMember
import com.giraffe.details.components.StarCastSection
import com.giraffe.details.components.sampleCastList


@Composable
@Preview(
    name = "AddToCollection Light",
    apiLevel = 34,
    showBackground = false,
    showSystemUi = false
)
fun PreviewAddToCollectionContentLight() {
    CineVerseTheme(isDarkTheme = false) {
        AddToCollectionContent(
            title = "My Folder",
            isLoading = true
        )
    }
}

@Composable
@Preview(
    name = "AddToCollection Dark",
    apiLevel = 34,
    showBackground = false,
    showSystemUi = false
)
fun PreviewAddToCollectionContentDark() {
    CineVerseTheme(isDarkTheme = true) {
        AddToCollectionContent(
            title = "My Folder",
            isLoading = true
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



@Composable
@Preview
fun MainDetailsPreview() {
    CineVerseTheme(isDarkTheme = true) {
        MainDetails(
            actorImage = painterResource(R.drawable.gallery_item),
            actorName = "Christian Bale",
            actorBirthday = "Jan 30, 1970",
            actorPlaceOfBirth = "Cardiff, Wales, UK",
            onInstagramClick = {},
            onYoutubeClick = {},
            onFacebookClick = {}
        )
    }
}

@Composable
@Preview
fun PreviewMainMovieDetails() {

    CineVerseTheme (
        isDarkTheme = true
    ) {
        MainMovieDetails(
            modifier = Modifier
                .width(360.dp),
            posterRes = com.giraffe.designsystem.R.drawable.main_poster_test,
            title = "The Dark Knight",
            genres = "Drama, Action, Crime, Thriller",
            rating = "8.5",
            duration = "2h 32m",
            releaseDate = "2008, Jul 18"
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
            reviewerUsername = "bilal_azzam"
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
            reviewerUsername = "bilal_azzam"
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
            poster = painterResource(
                id = com.giraffe.designsystem.R.drawable.reviewer
            ),
            title = "Season",
            caption = "Caption",
            rating = 7.5,
            episodes = 20,
            year = 2019
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
            poster = painterResource(
                id = com.giraffe.designsystem.R.drawable.reviewer
            ),
            title = "Season",
            caption = "Caption",
            rating = 7.5,
            episodes = 20,
            year = 2019
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
            onShowMoreClick = {},
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
            onShowMoreClick = {},
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