package com.giraffe.cineverseapp.util

import com.giraffe.cineverseapp.BuildConfig
import com.giraffe.presentation.profile.utils.AppVersionProvider
import jakarta.inject.Inject

class AppVersionProviderImpl @Inject constructor() : AppVersionProvider {
    override fun getVersionName(): String {
        return BuildConfig.VERSION_NAME
    }
}