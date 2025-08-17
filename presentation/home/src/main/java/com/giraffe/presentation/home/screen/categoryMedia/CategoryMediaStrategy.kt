package com.giraffe.presentation.home.screen.categoryMedia

import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.strategy.BasedOnTrueEventsStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.CinematicMasterpiecesStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.FamilyNightPicksStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.FeelGoodPreferencesStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.LateNightThrillsStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.MatchesYourVibesStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.MindBendingStoriesStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.RecentlyReleasedStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.RecentlyViewedStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.TopRatedTvShowsStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.UpcomingMoviesStrategy

interface CategoryMediaStrategy {
    suspend fun loadData(page: Int, pageSize: Int): List<PosterMedia>
    fun getSectionType(): CategoryMediaSectionType
}


class CategoryMediaFactory(
    private val recentlyReleasedStrategy: RecentlyReleasedStrategy,
    private val topRatedTvShowsStrategy: TopRatedTvShowsStrategy,
    private val upcomingMoviesStrategy: UpcomingMoviesStrategy,
    private val recentlyViewedStrategy: RecentlyViewedStrategy,
    private val matchesYourVibesStrategy: MatchesYourVibesStrategy,
    private val lateNightThrillsStrategy: LateNightThrillsStrategy,
    private val familyNightPicksStrategy: FamilyNightPicksStrategy,
    private val mindBendingStoriesStrategy: MindBendingStoriesStrategy,
    private val basedOnTrueEventsStrategy: BasedOnTrueEventsStrategy,
    private val cinematicMasterpiecesStrategy: CinematicMasterpiecesStrategy,
    private val feelGoodPreferencesStrategy: FeelGoodPreferencesStrategy
) {
    fun createStrategy(sectionType: CategoryMediaSectionType): CategoryMediaStrategy {
        return when (sectionType) {
            CategoryMediaSectionType.RECENTLY_RELEASED -> recentlyReleasedStrategy
            CategoryMediaSectionType.TOP_RATED_TV_SHOWS -> topRatedTvShowsStrategy
            CategoryMediaSectionType.UPCOMING_MOVIES -> upcomingMoviesStrategy
            CategoryMediaSectionType.RECENTLY_VIEWED -> recentlyViewedStrategy
            CategoryMediaSectionType.MATCHES_YOUR_VIBES -> matchesYourVibesStrategy
            CategoryMediaSectionType.LATE_NIGHT_THRILLS -> lateNightThrillsStrategy
            CategoryMediaSectionType.FAMILY_NIGHT_PICKS -> familyNightPicksStrategy
            CategoryMediaSectionType.MIND_BENDING_STORIES -> mindBendingStoriesStrategy
            CategoryMediaSectionType.BASED_ON_TRUE_EVENTS -> basedOnTrueEventsStrategy
            CategoryMediaSectionType.CINEMATIC_MASTERPIECE -> cinematicMasterpiecesStrategy
            CategoryMediaSectionType.FEEL_GOOD_PREFERENCES -> feelGoodPreferencesStrategy
        }
    }
}