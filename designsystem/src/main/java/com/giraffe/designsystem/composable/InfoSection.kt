package com.giraffe.designsystem.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giraffe.designsystem.text_style.manropeFontFamily
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun InfoSection(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    descriptionLimits: Int = 100
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary
        )
        Text(
            text = description,
            style = TextStyle(
                fontFamily = manropeFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
            ),
            maxLines = descriptionLimits,
            color = Theme.color.shade.secondary,
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInfoSection() {
    CineVerseTheme {
        InfoSection(
            title = "Storyline",
            description = "When they were boys, Sam and Dean Winchester lost their mother to a mysterious and demonic supernatural force. Subsequently, their father raised them to be soldiers. He taught them about the paranormal evil that lives in the dark corners and on the back roads of America ... and he taught them how to kill it. Now, the Winchester brothers crisscross the country in their '67 Chevy Impala, battling every kind of supernatural threat they encounter along the way.",
        )
    }
}