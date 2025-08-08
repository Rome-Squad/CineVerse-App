package com.giraffe.repository

import com.giraffe.repository.datasource.local.OnboardingLocalDataSource
import com.giraffe.repository.utils.safeCall
import com.giraffe.user.repository.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val localDataSource: OnboardingLocalDataSource
) : OnboardingRepository {

    override suspend fun setFirstTimeStatus() = safeCall {
        localDataSource.setOnBoardingFirstTime()
    }

    override suspend fun isFirstTime(): Boolean = safeCall {
        localDataSource.isOnBoardingFirstTime()
    }
}