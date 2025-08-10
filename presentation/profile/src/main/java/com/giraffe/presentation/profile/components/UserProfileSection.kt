package com.giraffe.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.modifier.noHoverClickable
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun UserProfileSection(
    modifier: Modifier = Modifier,
    userProfileImage: String,
    userDisplayName: String,
    username: String,
    onRowClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(
                    size = Theme.radius.lg
                )
            )
            .background(
                color = Theme.color.background.card
            )
            .padding(
                all = 16.dp
            )
            .noHoverClickable(
                onRowClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (userProfileImage.isNotBlank()) {
                AsyncImage(
                    model = userProfileImage,
                    contentDescription = userDisplayName,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(56.dp),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            shape = CircleShape,
                            color = Theme.color.shade.quinary
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Theme.icons.dueTone.profile),
                        contentDescription = "profile Image",
                        tint = Theme.color.shade.secondary
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = if (userDisplayName.isNotEmpty()) userDisplayName else stringResource(R.string.tap_to_add_your_name),
                    style = Theme.textStyle.body.md.medium,
                    color = Theme.color.shade.primary
                )

                Text(
                    text = username,
                    style = Theme.textStyle.body.sm.regular,
                    color = Theme.color.shade.secondary
                )
            }
        }

        Icon(
            painter = painterResource(
                id = R.drawable.outline_alt_arrow_right
            ),
            contentDescription = stringResource(
                id = R.string.right_arrow
            ),
            tint = Theme.color.shade.tertiary,
        )
    }
}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewUserProfileSection() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        UserProfileSection(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        size = Theme.radius.lg
                    )
                )
                .background(
                    color = Theme.color.background.screen
                )
                .padding(
                    all = 12.dp
                ),
            userProfileImage = "",
            userDisplayName = "Bilal Azzam",
            username = "bilal_azzam"
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewUserProfileSectionDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        UserProfileSection(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        size = Theme.radius.lg
                    )
                )
                .background(
                    color = Theme.color.background.screen
                )
                .padding(
                    all = 12.dp
                ),
            userProfileImage = "",
            userDisplayName = "Bilal Azzam",
            username = "bilal_azzam"
        )
    }
}
