package com.giraffe.designsystem.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.modifier.noHoverClickable
import com.giraffe.designsystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheet(
    content: @Composable () -> Unit,
    isVisible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
) {

    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            modifier = modifier.navigationBarsPadding(),
            dragHandle = {
                BottomSheetDefaults.DragHandle(
                    color = Theme.color.shade.quaternary
                )
            },
            shape = RoundedCornerShape(Theme.radius.xl),
            contentColor = Theme.color.background.bottomSheet,
            containerColor = Theme.color.background.bottomSheet,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(title != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = title.toString(),
                            style = Theme.textStyle.title.sm,
                            color = Theme.color.shade.primary,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            painter = painterResource(Theme.icons.outline.close),
                            contentDescription = stringResource(R.string.close),
                            tint = Theme.color.shade.secondary,
                            modifier = Modifier
                                .size(20.dp)
                                .noHoverClickable(
                                    onClick = { onDismiss() }
                                )
                        )
                    }
                }

                content()
            }
        }
    }
}