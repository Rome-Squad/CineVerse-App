package com.giraffe.presentation.details.screens.castDetails.state

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.details.R
import com.giraffe.media.mediaMember.entity.core.SocialMediaLinks

data class SocialMediaUi(
    val platform: SocialMediaPlatform,
    val url: String,
    val name: Int,
    val contentDescription: Int
)

enum class SocialMediaPlatform {
    YOUTUBE, FACEBOOK, INSTAGRAM, TWITTER, TIKTOK
}

@Composable
fun SocialMediaPlatform.getIcon(): Painter {
    return when (this) {
        SocialMediaPlatform.YOUTUBE -> painterResource(Theme.icons.colored.youtube)
        SocialMediaPlatform.FACEBOOK -> painterResource(Theme.icons.colored.facebook)
        SocialMediaPlatform.INSTAGRAM -> painterResource(Theme.icons.colored.instagram)
        SocialMediaPlatform.TWITTER -> painterResource(Theme.icons.colored.x)
        SocialMediaPlatform.TIKTOK -> painterResource(Theme.icons.colored.tiktok)
    }
}

fun SocialMediaLinks.toUiState(): List<SocialMediaUi> {
    val socialMediaUiList: MutableList<SocialMediaUi> = mutableListOf()
    youtubeLink?.let { url ->
        socialMediaUiList.add(
            SocialMediaUi(
                platform = SocialMediaPlatform.YOUTUBE,
                url = url,
                name = R.string.youtube,
                contentDescription = R.string.youtube_icon
            )
        )
    }

    facebookLink?.let { url ->
        socialMediaUiList.add(
            SocialMediaUi(
                platform = SocialMediaPlatform.FACEBOOK,
                url = url,
                name = R.string.facebook,
                contentDescription = R.string.facebook_icon
            )
        )
    }

    instagramLink?.let { url ->
        socialMediaUiList.add(
            SocialMediaUi(
                platform = SocialMediaPlatform.INSTAGRAM,
                url = url,
                name = R.string.instagram,
                contentDescription = R.string.instagram_icon
            )
        )
    }

    twitterLink?.let { url ->
        socialMediaUiList.add(
            SocialMediaUi(
                platform = SocialMediaPlatform.TWITTER,
                url = url,
                name = R.string.twitter,
                contentDescription = R.string.twitter_icon
            )
        )
    }

    tiktokLink?.let { url ->
        socialMediaUiList.add(
            SocialMediaUi(
                platform = SocialMediaPlatform.TIKTOK,
                url = url,
                name = R.string.tiktok,
                contentDescription = R.string.tiktok_icon
            )
        )
    }
    return socialMediaUiList
}

