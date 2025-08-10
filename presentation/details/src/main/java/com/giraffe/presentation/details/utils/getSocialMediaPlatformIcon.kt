package com.giraffe.presentation.details.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.details.model.SocialMediaPlatform


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