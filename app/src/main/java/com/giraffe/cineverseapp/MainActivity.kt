package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.giraffe.details.DetailsApi
//import com.giraffe.details.DetailsApi
import com.giraffe.details.screens.castDetails.DetailsApiImp
//import com.giraffe.explore.ExploreApi
//import com.giraffe.explore.ExploreApi
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {
//    private val exploreApi: ExploreApi by inject()
    private val detailsApi: DetailsApi = DetailsApiImp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            exploreApi.CreateSearchScreen { personId ->
                detailsApi.GetSeriesDetailsContainer(1022)
//            }
        }
    }
}

