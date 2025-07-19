package com.giraffe.cineverseapp.di

import com.giraffe.details.DetailsApi
import com.giraffe.details.navigation.DetailsApiImp
import com.giraffe.explore.ExploreApi
import com.giraffe.explore.navigation.ExploreApiImp
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureApiModule = module {
    single<DetailsApi> { DetailsApiImp() }
//    single<ExploreApi> { ExploreApiImp() }
    singleOf(::ExploreApiImp) bind ExploreApi::class
}