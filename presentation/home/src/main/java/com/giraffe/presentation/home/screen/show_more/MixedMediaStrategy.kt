package com.giraffe.presentation.home.screen.show_more

import com.giraffe.presentation.home.model.ShowMorePoster
import com.giraffe.presentation.home.navigation.home.routes.MixedMediaSectionType
import com.giraffe.presentation.home.screen.show_more.strategy.BasedOnTrueEventsStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.CinematicMasterpiecesStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.FamilyNightPicksStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.FeelGoodPreferencesStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.LateNightThrillsStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.MatchesYourVibesStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.MindBendingStoriesStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.RecentlyReleasedStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.RecentlyViewedStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.TopRatedTvShowsStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.UpcomingMoviesStrategy

interface MixedMediaStrategy {
    suspend fun loadData(page: Int, pageSize: Int): List<ShowMorePoster>
    fun getSectionType(): MixedMediaSectionType
}


class MixedMediaFactory(
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
    fun createStrategy(sectionType: MixedMediaSectionType): MixedMediaStrategy {
        return when (sectionType) {
            MixedMediaSectionType.RECENTLY_RELEASED -> recentlyReleasedStrategy
            MixedMediaSectionType.TOP_RATED_TV_SHOWS -> topRatedTvShowsStrategy
            MixedMediaSectionType.UPCOMING_MOVIES -> upcomingMoviesStrategy
            MixedMediaSectionType.RECENTLY_VIEWED -> recentlyViewedStrategy
            MixedMediaSectionType.MATCHES_YOUR_VIBES -> matchesYourVibesStrategy
            MixedMediaSectionType.LATE_NIGHT_THRILLS -> lateNightThrillsStrategy
            MixedMediaSectionType.FAMILY_NIGHT_PICKS -> familyNightPicksStrategy
            MixedMediaSectionType.MIND_BENDING_STORIES -> mindBendingStoriesStrategy
            MixedMediaSectionType.BASED_ON_TRUE_EVENTS -> basedOnTrueEventsStrategy
            MixedMediaSectionType.CINEMATIC_MASTERPIECE -> cinematicMasterpiecesStrategy
            MixedMediaSectionType.FEEL_GOOD_PREFERENCES -> feelGoodPreferencesStrategy
        }
    }
}