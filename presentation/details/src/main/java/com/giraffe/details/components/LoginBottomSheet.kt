package com.giraffe.details.components

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.MessageInfoBox
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R

@androidx.compose.runtime.Composable
fun LoginBottomSheet(
    isVisible: Boolean,
    onLogInClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    BaseBottomSheet(
        isVisible = isVisible,
        onDismiss = onDismiss,
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
        content = {
            MessageInfoBox(
                title = stringResource(R.string.you_re_almost_there),
                caption = stringResource(R.string.log_in_to_save_movies_create_collections_and_get_personalized_recommendations),
                icon = painterResource(Theme.icons.dueTone.login),
                iconTintColor = Theme.color.brand.tertiary,
                buttonBackgroundColor = Theme.color.brand.primary,
                iconBackgroundColor = Theme.color.brand.primary,
                titlePrimaryButton = stringResource(R.string.log_in),
                titleSecondaryButton = stringResource(R.string.not_now),
                onClickPrimaryButton = onLogInClick,
                onClickSecondaryButton = onDismiss
            )
        }
    )
}