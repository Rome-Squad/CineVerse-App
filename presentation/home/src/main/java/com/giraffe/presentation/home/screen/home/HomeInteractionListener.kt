package com.giraffe.presentation.home.screen.home

import com.giraffe.presentation.home.screen.show_more.ShowMoreSectionType

interface HomeInteractionListener {
    fun onMediaClicked(mediaId: Int, mediaType: MediaType)
    fun loadHomeContent()
    fun onSeeAllRecentlyReleasedClicked( sectionType:ShowMoreSectionType)
    fun onSeeAllTopRatedClicked( sectionType:ShowMoreSectionType)
    fun onSeeAllUpcomingClicked( sectionType:ShowMoreSectionType)
    fun onSeeAllRecentlyViewedClicked(sectionType: ShowMoreSectionType)
    fun onWhatShouldIWatchClicked(sectionType: ShowMoreSectionType)
    fun onFeaturedCollectionClicked(collectionId: Int, collectionTitle: String)
    fun onYourCollectionClicked()
    fun onExploreSectionClicked()
    fun onMatchSectionClicked()

    fun onCollectionClick(
        collectionId: Int,
        collectionName: String
    )
}