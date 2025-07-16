package com.giraffe.details.screens.gallery

import com.giraffe.details.base.BaseViewModel

class GalleryViewModel(
    images: List<String> = emptyList(),
) : BaseViewModel<GalleryUiState, GalleryEffect>(
    GalleryUiState()
) {

}