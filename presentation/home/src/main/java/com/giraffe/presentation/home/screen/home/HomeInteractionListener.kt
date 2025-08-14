package com.giraffe.presentation.home.screen.home

import com.giraffe.presentation.home.model.MediaType
import com.giraffe.presentation.home.navigation.home.routes.ShowMoreSectionType

interface HomeInteractionListener {
    fun onMediaClicked(mediaId: Int, mediaType: MediaType)
    fun onSeeAllRecentlyReleasedClicked(sectionType: ShowMoreSectionType)
    fun onSeeAllTopRatedClicked(sectionType: ShowMoreSectionType)
    fun onSeeAllUpcomingClicked(sectionType: ShowMoreSectionType)
    fun onSeeAllRecentlyViewedClicked(sectionType: ShowMoreSectionType)
    fun onMatchYourVibeClicked(sectionType: ShowMoreSectionType)
    fun onFeaturedCollectionClicked(collectionId: Int, collectionTitle: String)
    fun onYourCollectionClicked()
    fun onExploreSectionClicked()
    fun onMatchSectionClicked()

    fun onCollectionClick(
        collectionId: Int,
        collectionName: String
    )
}