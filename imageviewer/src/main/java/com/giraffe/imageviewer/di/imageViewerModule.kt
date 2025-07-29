package com.giraffe.imageviewer.di


import android.content.Context
import com.giraffe.imageviewer.mlmodel.SafeIslamicImageClassifier
import com.giraffe.imageviewer.mlmodel.SafeIslamicImageClassifierImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageViewerModule {

    @Provides
    @Singleton
    fun provideSafeIslamicImageClassifier(
        @ApplicationContext context: Context
    ): SafeIslamicImageClassifier {
        return SafeIslamicImageClassifierImpl(context)
    }
}