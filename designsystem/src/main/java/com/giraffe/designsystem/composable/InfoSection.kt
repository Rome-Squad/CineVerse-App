package com.giraffe.designsystem.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun InfoSection(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    descriptionLimits: Int = 100,
    spaceBetween: Int = 8
) {
    if (description.isNotBlank()) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(spaceBetween.dp)
        ) {
            Text(
                text = title,
                style = Theme.textStyle.title.sm,
                color = Theme.color.shade.primary
            )
            Text(
                text = description,
                style = Theme.textStyle.body.sm.medium,
                maxLines = descriptionLimits,
                color = Theme.color.shade.secondary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CineVerseTheme {
        InfoSection(
            modifier = Modifier.fillMaxWidth(),
            title = "Storyline",
            description = "When they were boys, Sam and Dean Winchester lost their mother to a mysterious and demonic supernatural force. Subsequently, their father raised them to be soldiers. He taught them about the paranormal evil that lives in the dark corners and on the back roads of America ... and he taught them how to kill it. Now, the Winchester brothers crisscross the country in their '67 Chevy Impala, battling every kind of supernatural threat they encounter along the way.",
        )
    }
}