package com.giraffe.home.screen.home

interface HomeInteractionListener {
    fun onMediaClicked(mediaId: Int, mediaType: MediaType)
    fun loadHomeContent()
    fun onSeeAllRecentlyReleasedClicked()
    fun onSeeAllTopRatedClicked()
    fun onSeeAllUpcomingClicked()
    fun onSeeAllRecentlyViewedClicked()
    fun onWhatShouldIWatchClicked(sectionTitle: String, sectionType: String)
    fun onFeaturedCollectionClicked(collectionId: Int, collectionTitle: String)
}