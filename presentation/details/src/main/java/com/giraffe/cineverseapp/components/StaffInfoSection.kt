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
    staffList: List<StaffMember>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Theme.color.shade.primary,
                style = Theme.textStyle.title.sm,
            )

            Text(
                text = "Show More",
                color = Theme.color.brand.primary,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clickable { onShowMoreClick() },
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
                staffList.forEachIndexed { index, staff ->
                    StaffItem(name = staff.name, role = staff.role)

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
data class StaffMember(
    val name: String,
    val role: String
)
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
    CinVerseTheme(isDarkTheme = false) {
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