package com.giraffe.details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun MinimizedInfoRow(posterUrl: Int,title:String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MiniMoviePoster(posterUrl)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))

            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                title,
                modifier = Modifier.padding(start = 12.dp),
                style = Theme.textStyle.title.sm,
                color = Theme.color.shade.primary
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                RoundedIconButton(
                    icon = painterResource(Theme.icons.dueTone.add),
                    backgroundColor = Color(0xFF24263B)
                )
                RoundedIconButton(
                    icon = painterResource(Theme.icons.dueTone.play),
                    backgroundColor = Color(0xFF8C9EFF),
                    iconPaddingTop = 12.dp,
                    iconPaddingBottom = 12.dp
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
fun PreviewMinimizedInfoRow() {

    CineVerseTheme (
        isDarkTheme = true
    ) {
        MinimizedInfoRow(
            posterUrl = R.drawable.main_poster_test,
            title = "The Dark Knight")

    }
}