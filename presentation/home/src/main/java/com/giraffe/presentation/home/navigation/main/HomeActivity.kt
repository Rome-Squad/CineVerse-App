package com.giraffe.presentation.home.navigation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.giraffe.api.details.DetailsApi
import com.giraffe.api.explore.ExploreApi
import com.giraffe.api.home.HomeApi
import com.giraffe.api.profile.ProfileApi
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.match.MatchApi
import com.giraffe.presentation.home.navigation.api.HomeApiImp
import com.giraffe.presentation.home.screen.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import jakarta.inject.Inject
import javax.inject.Provider


@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    @Inject
    lateinit var detailsApi: DetailsApi

    @Inject
    lateinit var exploreApi: ExploreApi

    @Inject
    lateinit var profileApi: ProfileApi

    @Inject
    lateinit var matchApi: MatchApi

    @Inject
    lateinit var homeApi: HomeApi

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            LaunchedEffect(Unit) {
                (homeApi as HomeApiImp).setNavController(navController)
            }
            CineVerseTheme(isDarkTheme = false) {

                MainNavGraph(
                    navController = navController,
                    detailsApi = detailsApi,
                    exploreApi = exploreApi,
                    profileApi = profileApi,
                    matchApi = matchApi
                )
            }
        }
    }
}