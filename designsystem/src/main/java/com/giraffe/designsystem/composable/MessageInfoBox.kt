package com.giraffe.designsystem.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.composable.button_type.SecondaryButton
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun MessageInfoBox(
    title: String,
    caption: String,
    icon: Painter,
    iconTint: Color,
    iconBackgroundColor: Color,
    isButtonsVisible: Boolean = true,
    titlePrimaryButton: String = "",
    titleSecondaryButton: String = "",
    modifier: Modifier = Modifier,
    onClickPrimaryButton: () -> Unit = {},
    onClickSecondaryButton: () -> Unit = {}
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = icon,
            contentDescription = "No Messages",
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(Theme.radius.full))
                .background(iconBackgroundColor)
                .padding(18.dp),
            tint = iconTint
        )


        Text(
            text = title,
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = caption,
            style = Theme.textStyle.body.sm.medium,
            color = Theme.color.shade.secondary,
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
        if (isButtonsVisible) Row(
            modifier = Modifier.padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SecondaryButton(
                text = titleSecondaryButton,
                onClick = onClickSecondaryButton,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            )
            PrimaryButton(
                text = titlePrimaryButton,
                onClick = onClickPrimaryButton,
                buttonColorEnabled = iconTint,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1B1C2A)
@Composable
fun PreviewEditProfileMessage() {
    CineVerseTheme(isDarkTheme = true) {
        MessageInfoBox(
            title = "Edit Profile",
            caption = "You’ll be taken to the website to update your name, username, or profile picture.",
            icon = painterResource(Theme.icons.dueTone.linkMinimalistic),
            iconTint = Theme.color.brand.primary,
            iconBackgroundColor = Theme.color.brand.tertiary,
            titlePrimaryButton = "Go to Website",
            titleSecondaryButton = "Cancel",
            modifier = Modifier.size(304.dp, 214.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1B1C2A)
@Composable
fun PreviewLogoutMessage() {
    CineVerseTheme(isDarkTheme = true) {
        MessageInfoBox(
            title = "Are you sure you want to logout?",
            caption = "You can always sign back in with your account.",
            icon = painterResource(Theme.icons.dueTone.linkMinimalistic),
            iconTint = Theme.color.additional.primary.red,
            iconBackgroundColor = Theme.color.additional.secondary.red,
            titlePrimaryButton = "Logout",
            titleSecondaryButton = "Cancel",
            modifier = Modifier.width(304.dp)
        )
    }
}