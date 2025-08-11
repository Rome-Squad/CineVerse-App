package com.giraffe.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.profile.R

@Composable
fun DeleteButton(modifier: Modifier = Modifier, onDeleteClick: () -> Unit = {}) {
    Box(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .size(48.dp)
            .background(
                color = Theme.color.additional.primary.red,
                shape = RoundedCornerShape(Theme.radius.lg)
            )
            .clip(RoundedCornerShape(Theme.radius.lg))
            .clickable(onClick = onDeleteClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = modifier.size(20.dp),
            painter = painterResource(Theme.icons.dueTone.trash),
            tint = Theme.color.button.onPrimary,
            contentDescription = stringResource(R.string.trash)
        )
    }
}