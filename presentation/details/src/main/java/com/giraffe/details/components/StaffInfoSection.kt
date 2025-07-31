package com.giraffe.details.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.CustomCard
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R

@Composable
fun StaffInfoSection(
    title: String,
    staffList: Map<String, List<String>>,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(staffList.isNotEmpty()) {
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
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            CustomCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(Theme.radius.lg),
            ) {
                Column {
                    staffList.entries.toList().forEachIndexed { index, (key, value) ->
                        val name = value.joinToString(stringResource(R.string.split))
                        StaffItem(name = name, role = key)

                        if (index != staffList.entries.size - 1) {
                            Box(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(Theme.color.stroke.primary)
                            )
                        }
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
            .padding(horizontal = 16.dp, vertical = 18.5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = role,
            modifier = Modifier.weight(1f),
            style = Theme.textStyle.body.md.regular,
            color = Theme.color.shade.secondary,
            textAlign = TextAlign.Start,
        )
        Text(
            text = name,
            style = Theme.textStyle.body.md.medium,
            color = Theme.color.shade.primary,
            maxLines = 2,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            overflow = TextOverflow.Ellipsis,
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
    // apiLevel = 34
)
@Composable
fun PreviewStaffInfoSectionDark() {
    CineVerseTheme(isDarkTheme = true) {
        StaffInfoSection(
            title = "Behind the Scenes",
            staffList = groupedStaff
        )
    }
}

@Preview(
    name = "StaffInfoSection Preview - Light",
    showBackground = true,
)
@Composable
fun PreviewStaffInfoSectionLight() {
    CineVerseTheme(isDarkTheme = false) {
        StaffInfoSection(
            title = "Behind the Scenes",
            staffList = groupedStaff
        )
    }
}

val staffList = listOf(
    StaffMember(name = "John Doe", role = "Director"),
    StaffMember(name = "Christopher Nolan", role = "Director, Screenplay, Story"),
    StaffMember(name = "Mike Johnson", role = "Writer")
)

val groupedStaff: Map<String, List<String>> = staffList
    .flatMap { staff ->
        staff.role.split(", ").map { role ->
            role to staff.name
        }
    }
    .groupBy(
        keySelector = { it.first },
        valueTransform = { it.second }
    )