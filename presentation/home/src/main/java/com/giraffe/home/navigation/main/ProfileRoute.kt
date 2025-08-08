package com.giraffe.home.navigation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.composable.navbar.BottomTab
import com.giraffe.designsystem.composable.navbar.Route
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.Theme
import kotlinx.serialization.Serializable


@Serializable
object ProfileRoute: Route("profile")

class ProfileTab(
    override val labelRes: Int,
    override val iconRes: Int
) : BottomTab<ProfileRoute> {
    override val route = ProfileRoute
}


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onShowBottomBarChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = stringResource(
                id = R.string.me
            ),
        )
    }
}