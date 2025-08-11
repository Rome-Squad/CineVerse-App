package com.giraffe.presentation.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.presentation.details.R

@Composable
fun GiveStarsBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    currentRating: Int,
    onRateChange: (Int) -> Unit,
    onAddRateButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.rate_the_movie)
) {
    BaseBottomSheet(
        isVisible = isVisible,
        onDismiss = onDismiss,
        title = title,
        modifier = modifier,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RatingSelector(
                    rate = currentRating,
                    onRateClick = onRateChange
                )
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    text = stringResource(R.string.add_to_rate),
                    enabled = currentRating > 0,
                    onClick = onAddRateButtonClick
                )
            }
        }
    )
}