package com.giraffe.details.components.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.details.R


@Composable
fun GallerySection(
    modifier: Modifier = Modifier,
    imageUrls: List<String?>,
    onShowMoreClick: () -> Unit,
) {
    if (imageUrls.isNotEmpty()) {
        val showMoreText =
            imageUrls.size.takeIf { it > 3 }?.let { stringResource(R.string.show_more) }
        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SectionTitle(
                title = stringResource(R.string.gallery),
                clickableText = showMoreText,
                onClickableText = onShowMoreClick,
                modifier = Modifier.fillMaxWidth()
            )
            GalleryItemLayoutLTR(imageUrls = imageUrls)
        }
    }
}

@Preview
@Composable
fun GallerySectionPreview() {
    CineVerseTheme(isDarkTheme = true) {
        GallerySection(
            modifier = Modifier
                .height(314.dp)
                .fillMaxWidth(),
            imageUrls = listOf(
                null,
                null,
                "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg"
            ),
            onShowMoreClick = {}
        )
    }
}