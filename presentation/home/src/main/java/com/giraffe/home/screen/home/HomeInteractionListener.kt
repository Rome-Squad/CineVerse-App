package com.giraffe.home.screen.home

interface HomeInteractionListener {
    fun onMediaClicked(mediaId: Int, mediaType: MediaType)
    fun loadHomeContent()
    fun onSeeAllRecentlyReleasedClicked(sectionTitle: String, sectionType: String)
    fun onSeeAllTopRatedClicked()
    fun onSeeAllUpcomingClicked()
    fun onSeeAllRecentlyViewedClicked(sectionTitle: String, sectionType: String)
    fun onWhatShouldIWatchClicked(sectionTitle: String, sectionType: String)
    fun onFeaturedCollectionClicked(collectionId: Int, collectionTitle: String)
}