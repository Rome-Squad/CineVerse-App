package com.giraffe.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.home.R

@Composable
fun ListTitleSection(onBackClick: () -> Unit, title: String) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 17.dp),
        horizontalArrangement = Arrangement.spacedBy(9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.clickable(onClick = onBackClick),
            painter = painterResource(Theme.icons.outline.arrowLeft),
            contentDescription = stringResource(R.string.back),
            tint = Theme.color.shade.primary
        )
        Text(
            text = title,
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary
        )
    }
}