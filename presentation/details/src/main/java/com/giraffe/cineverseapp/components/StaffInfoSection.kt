package com.giraffe.cineverseapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.presentation.designsystem.theme.CinVerseTheme
import com.giraffe.presentation.designsystem.theme.Theme

@Composable
fun StaffInfoSection(
    title: String,
    onShowMoreClick: () -> Unit,
    staffList: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Theme.color.shade.primary,
                style = Theme.textStyle.title.sm
            )

            Text(
                text = "Show More",
                color = Theme.color.brand.primary,
                modifier = Modifier
                    .clickable { onShowMoreClick() }
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                style = Theme.textStyle.body.medium.medium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Theme.color.background.card)
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                staffList.forEachIndexed { index, (name, role) ->
                    StaffItem(name = name, role = role)

                    if (index != staffList.lastIndex) {
                        HorizontalDivider(
                            thickness = 0.5.dp,
                            color = Theme.color.stroke.primary,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun StaffItem(name: String, role: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = role,
            modifier = Modifier.weight(1f),
            style = Theme.textStyle.body.medium.regular,
            color = Theme.color.shade.secondary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = name,
            style = Theme.textStyle.body.medium.medium,
            color = Theme.color.shade.primary
        )
    }
}
@Preview(
    name = "StaffInfoSection Preview - Dark",
    showBackground = false,
    apiLevel = 34
)
@Composable
fun PreviewStaffInfoSectionDark() {
    CinVerseTheme(isDarkTheme = true) {
        StaffInfoSection(
            title = "Staff Info",
            onShowMoreClick = {},
            staffList = listOf(
                "John Doe" to "Director",
                "Christopher Nolan" to "Director, Screenplay, Story",
                "Mike Johnson" to "Writer"
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
    CinVerseTheme(isDarkTheme = false) {
        StaffInfoSection(
            title = "Staff Info",
            onShowMoreClick = {},
            staffList = listOf(
                "John Doe" to "Director",
                "Christopher Nolan" to "Director, Screenplay, Story",
                "Mike Johnson" to "Writer"
            )
        )
    }
}