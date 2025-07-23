package com.giraffe.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.component.SafeIslamicImage

@Composable
fun UserProfileSection(
    modifier: Modifier = Modifier,
    userProfileImage: Any? = null,
    userDisplayName: String,
    username: String,
    onArrowButtonClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            modifier = Modifier
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
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SafeIslamicImage(
                    imageUrl = userProfileImage.toString(),
                    contentDescription = userProfileImage.toString(),
                    modifier = modifier
                        .clip(CircleShape)
                        .size(56.dp),
                    contentScale = ContentScale.Crop
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = userDisplayName,
                        style = Theme.textStyle.body.md.medium,
                        color = Theme.color.shade.primary
                    )


                    Text(
                        text = stringResource(R.string.user_name, username),
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
                modifier = Modifier
                    .clickable {
                        onArrowButtonClick()
                    }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconTextBox(
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(
                            size = Theme.radius.full
                        )
                    )
                    .background(
                        color = Theme.color.background.card
                    )
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    ),
                text = stringResource(R.string.history)
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.outline_history
                    ),
                    contentDescription = stringResource(R.string.history),
                    tint = Theme.color.brand.primary
                )
            }

            IconTextBox(
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(
                            size = Theme.radius.full
                        )
                    )
                    .background(
                        color = Theme.color.background.card
                    )
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    ),
                text = stringResource(R.string.my_collections)
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.due_tone_video_library
                    ),
                    contentDescription = stringResource(R.string.my_collections),
                    tint = Theme.color.brand.primary
                )
            }

            IconTextBox(
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(
                            size = Theme.radius.full
                        )
                    )
                    .background(
                        color = Theme.color.background.card
                    )
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    ),
                text = stringResource(R.string.my_ratings)
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.due_tone_star
                    ),
                    contentDescription = stringResource(R.string.my_ratings),
                    tint = Theme.color.brand.primary
                )
            }
        }
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
            userProfileImage = R.drawable.reviewer,
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
            userProfileImage = R.drawable.reviewer,
            userDisplayName = "Bilal Azzam",
            username = "bilal_azzam"
        )
    }
}
