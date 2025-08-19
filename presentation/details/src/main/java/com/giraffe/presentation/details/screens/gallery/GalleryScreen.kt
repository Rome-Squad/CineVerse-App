package com.giraffe.presentation.details.screens.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.base.BaseScreen
import com.giraffe.presentation.details.components.gallery.GalleryItemLayoutLTR
import com.giraffe.presentation.details.components.gallery.GalleryItemLayoutRTL
import com.giraffe.presentation.details.utils.EventListener
import com.giraffe.presentation.details.utils.showToast
import com.giraffe.presentation.details.utils.toStringResource

@Composable
fun GalleryScreen(
    onBackClick: () -> Unit,
    galleryViewModel: GalleryViewModel = hiltViewModel()
) {
    val state by galleryViewModel.state.collectAsState()
    val context = LocalContext.current

    EventListener(
        events = galleryViewModel.effect
    ) { effect ->
        when (effect) {
            is GalleryEffect.ShowError -> context.showToast(
                effect.error.toStringResource()
            )

            GalleryEffect.NavigateBack -> onBackClick()
        }

    }

    GalleryContent(
        state = state,
        interaction = galleryViewModel,
    )
}

@Composable
fun GalleryContent(
    state: GalleryUiState,
    interaction: GalleryInteractionListener,
) {
    BaseScreen(
        title = state.actorName + " " + stringResource(R.string.gallery),
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet,
        onRetryClick = interaction::onRetryClick,
        onBackClick = interaction::onBackClick,
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize(),
        ) {
            items(state.imageUrls.chunked(6)) { subImageUrls ->
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
}
