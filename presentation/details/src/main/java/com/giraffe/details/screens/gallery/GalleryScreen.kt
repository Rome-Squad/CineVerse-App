package com.giraffe.details.screens.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.components.gallery.GalleryItemLayoutLTR
import com.giraffe.details.components.gallery.GalleryItemLayoutRTL

@Composable
fun GalleryScreen(
    actorName: String,
    imageUrls: List<String?>,
    modifier: Modifier = Modifier,
) {
    val state: GalleryUiState by remember {
        mutableStateOf(
            GalleryUiState(
                actorName = actorName,
                imageUrls = imageUrls
            )
        )
    }
    GalleryContent(state = state)
}

@Composable
fun GalleryContent(
    state: GalleryUiState
) {
    Column(
        modifier = Modifier
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {
        AppBar(
            title = state.actorName + " " + stringResource(R.string.gallery),
            showBackButton = true,
            hasBackground = false,
            modifier = Modifier.padding(horizontal = 16.dp)
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
