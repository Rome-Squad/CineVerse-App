package com.giraffe.repository.datasource.local

interface OnboardingLocalDataSource {
    suspend fun setOnBoardingFirstTime()
    suspend fun isOnBoardingFirstTime(): Boolean
}