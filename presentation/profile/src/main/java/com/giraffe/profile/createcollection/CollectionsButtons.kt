package com.giraffe.profile.createcollection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.composable.button_type.SecondaryButton
import com.giraffe.profile.R

@Composable
fun CollectionsButtons(
    modifier: Modifier = Modifier,
    createButtonClick: () -> Unit,
    cancelButtonClick: () -> Unit,
    enableCreate: Boolean
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        SecondaryButton(
            modifier = Modifier
                .height(48.dp)
                .weight(1f),
            text = stringResource(R.string.cancel),
            onClick = cancelButtonClick,
            )

        PrimaryButton(
            modifier = Modifier
                .height(48.dp)
                .weight(1f),
            text = stringResource(R.string.create),
            onClick = createButtonClick,
            enabled = enableCreate
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CollectionsButtons(createButtonClick = {}, cancelButtonClick = {}, enableCreate = true)
}