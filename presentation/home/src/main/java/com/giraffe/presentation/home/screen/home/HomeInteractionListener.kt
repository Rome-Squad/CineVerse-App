package com.giraffe.presentation.home.screen.home

import com.giraffe.presentation.home.model.MediaType
import com.giraffe.presentation.home.navigation.home.routes.MixedMediaSectionType

interface HomeInteractionListener {

    fun onMediaClicked(mediaId: Int, mediaType: MediaType)

    fun onSeeMoreClicked(sectionType: MixedMediaSectionType)

    fun onFeaturedCollectionClicked(collectionId: Int, collectionTitle: String)

    fun onYourCollectionClicked()

    fun onExploreSectionClicked()

    fun onMatchSectionClicked()

    fun onRetryClick()

    fun onCollectionClick(collectionId: Int, collectionName: String)

    fun onFeaturesCollectionClicked(sectionType: MixedMediaSectionType)
}