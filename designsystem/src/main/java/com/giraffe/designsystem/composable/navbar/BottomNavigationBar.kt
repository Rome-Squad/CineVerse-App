package com.giraffe.designsystem.composable.navbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.BuildingBlock
import com.giraffe.designsystem.modifier.noHoverClickable
import com.giraffe.designsystem.theme.Theme

@Composable
fun BottomNavigationBar(
    tabs: List<BottomTab>,
    selectedTabRoute: Route?,
    onTabSelected: (BottomTab) -> Unit,
    modifier: Modifier = Modifier,
    isBottomBarVisible: Boolean = true
) {
    AnimatedVisibility(
        visible = isBottomBarVisible,
        enter = slideInVertically(animationSpec = tween(200, easing = LinearEasing)) { it },
        exit = slideOutVertically(animationSpec = tween(200, easing = LinearEasing)) { it }
    ) {
        Column(
            modifier = modifier
                .background(Theme.color.background.card)
                .navigationBarsPadding(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Theme.color.stroke.primary)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                tabs.forEach { tab ->
                    val isSelected = selectedTabRoute == tab.route
                    BuildingBlock(
                        icons = tab.iconRes,
                        label = stringResource(tab.labelRes),
                        isSelected = isSelected,
                        modifier = Modifier
                            .weight(1f)
                            .noHoverClickable { onTabSelected(tab) }
                            .padding(vertical = 13.dp)
                    )
                }
            }
        }
    }
}
