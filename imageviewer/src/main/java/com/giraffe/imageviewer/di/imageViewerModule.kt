package com.giraffe.imageviewer.di


import android.content.Context
import coil.ImageLoader
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
    ): SafeIslamicImageClassifier = SafeIslamicImageClassifierImpl(context)

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context
    ): ImageLoader = ImageLoader.Builder(context).build()
}