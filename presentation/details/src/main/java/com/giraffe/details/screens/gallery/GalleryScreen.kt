package com.giraffe.details.screens.gallery

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.components.LoadingView
import com.giraffe.details.components.gallery.GalleryItemLayoutLTR
import com.giraffe.details.components.gallery.GalleryItemLayoutRTL

@Composable
fun GalleryScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    galleryViewModel: GalleryViewModel = hiltViewModel()
) {
    val state by galleryViewModel.state.collectAsState()
    if (state.isLoading) {
        LoadingView()
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Theme.color.background.screen)
                .wrapContentSize()
        ) {
            GalleryContent(
                state = state,
                onBackArrowClick = onBackClick,
                modifier = modifier
            )
            AnimatedVisibility(
                visible = !state.errorMessage.isNullOrBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .background(Theme.color.shade.primary)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = state.errorMessage
                        ?: stringResource(R.string.unknown_error),
                    color = Theme.color.additional.primary.red,
                    textAlign = TextAlign.Center,
                    style = Theme.textStyle.label.md.regular
                )
            }
        }
    }
}

@Composable
fun GalleryContent(
    state: GalleryUiState,
    onBackArrowClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {
        AppBar(
            title = state.actorName + " " + stringResource(R.string.gallery),
            showBackButton = true,
            hasBackground = false,
            onBackButtonClick = onBackArrowClick,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        ImageGalleryLayout(imageUrls = state.imageUrls)
    }
}

@Composable
fun ImageGalleryLayout(imageUrls: List<String?>) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(imageUrls.chunked(6)) { subImageUrls ->
            if (subImageUrls.take(3).isNotEmpty()) {
                GalleryItemLayoutLTR(
                    imageUrls = subImageUrls.take(3),
                    modifier = Modifier
                        .height(280.dp)
                        .padding(top = 6.dp, bottom = 6.dp),
                )
                if (subImageUrls.size > 3) {
                    GalleryItemLayoutRTL(
                        imageUrls = subImageUrls.subList(3, subImageUrls.size),
                        modifier = Modifier
                            .height(280.dp)
                            .padding(top = 6.dp, bottom = 6.dp),
                    )
                }
            }
        }
    }
}
