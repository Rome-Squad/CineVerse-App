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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.components.gallery.GalleryItemLayoutLTR
import com.giraffe.details.components.gallery.GalleryItemLayoutRTL
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Preview
@Composable
fun GalleryScreen(
    images: List<String?> = emptyList(),
    galleryViewModel: GalleryViewModel = koinViewModel(parameters = { parametersOf(images) })
) {
    val state by galleryViewModel.state.collectAsState()
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
            title = state.actorName,
            showBackButton = true,
            hasBackground = false,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        ImageGalleryLayout(images = state.images)
    }
}

@Composable
fun ImageGalleryLayout(images: List<String>) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(images.chunked(6)) { subImages ->
            if (subImages.take(3).isNotEmpty()) {
                GalleryItemLayoutLTR(
                    images = subImages.take(3),
                    modifier = Modifier
                        .height(280.dp)
                        .padding(top = 6.dp, bottom = 6.dp),
                )
                if (subImages.size > 3) {
                    GalleryItemLayoutRTL(
                        images = subImages.subList(3, subImages.size),
                        modifier = Modifier
                            .height(280.dp)
                            .padding(top = 6.dp, bottom = 6.dp),
                    )
                }
            }
        }
    }
}
