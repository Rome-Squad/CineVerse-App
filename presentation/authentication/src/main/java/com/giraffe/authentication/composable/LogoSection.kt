package com.giraffe.authentication.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme

@Composable
fun LogoSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.colored_cineverse_logo),
            contentDescription = "logo",
            modifier = Modifier
                .padding(top = 48.dp, bottom = 8.dp)
                .size(104.dp)
        )

        Text(
            text = "Welcome back to CineVerse!",
            style = Theme.textStyle.title.lg,
            modifier = Modifier.padding(bottom = 48.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LogoSectionPreview() {
    LogoSection()
}
