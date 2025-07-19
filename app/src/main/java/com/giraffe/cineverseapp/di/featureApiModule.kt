package com.giraffe.cineverseapp.di

import com.giraffe.details.DetailsApi
import com.giraffe.details.DetailsApiImp
import com.giraffe.explore.ExploreApi
import com.giraffe.explore.ExploreApiImp
import org.koin.dsl.module

val featureApiModule = module {
    single<DetailsApi> { DetailsApiImp() }
    single<ExploreApi> { ExploreApiImp() }
}