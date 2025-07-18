package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.giraffe.details.DetailsApi
import com.giraffe.details.navigation.DetailsApiImpl


class MainActivity : ComponentActivity() {
    val detailsApi: DetailsApi = DetailsApiImpl()
//    val exploreApi = ExploreApiImpl(detailsApi)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            detailsApi.GetSeriesDetailsContainer(1022)
        }
    }
}

