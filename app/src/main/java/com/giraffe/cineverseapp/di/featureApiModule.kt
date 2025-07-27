package com.giraffe.cineverseapp.di

import com.giraffe.authentication.AuthenticationApi
import com.giraffe.authentication.nav.AuthenticationApiImp
import com.giraffe.details.DetailsApi
import com.giraffe.details.nav.DetailsApiImp
import com.giraffe.explore.ExploreApi
import com.giraffe.explore.navigation.ExploreApiImp
import com.giraffe.home.HomeApi
import com.giraffe.home.navigation.HomeApiImp
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureApiModule = module {
    singleOf(::AuthenticationApiImp) bind AuthenticationApi::class
    singleOf(::DetailsApiImp) bind DetailsApi::class
    singleOf(::ExploreApiImp) bind ExploreApi::class
    singleOf(::HomeApiImp) bind HomeApi::class
}