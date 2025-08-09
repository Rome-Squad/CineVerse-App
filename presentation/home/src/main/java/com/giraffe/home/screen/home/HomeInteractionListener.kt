package com.giraffe.home.screen.home

import com.giraffe.home.screen.show_more.ShowMoreSectionType

interface HomeInteractionListener {
    fun onMediaClicked(mediaId: Int, mediaType: MediaType)
    fun loadHomeContent()
    fun onSeeAllRecentlyReleasedClicked(sectionTitle: String, sectionType:ShowMoreSectionType)
    fun onSeeAllTopRatedClicked(sectionTitle: String, sectionType:ShowMoreSectionType)
    fun onSeeAllUpcomingClicked(sectionTitle: String, sectionType:ShowMoreSectionType)
    fun onSeeAllRecentlyViewedClicked(sectionTitle: String, sectionType: ShowMoreSectionType)
    fun onWhatShouldIWatchClicked(sectionTitle: String, sectionType: ShowMoreSectionType)
    fun onFeaturedCollectionClicked(collectionId: Int, collectionTitle: String)
    fun onYourCollectionClicked()
    fun onExploreSectionClicked()
    fun onMatchSectionClicked()

    fun onCollectionClick(
        collectionId: Int,
        collectionName: String
    )
}