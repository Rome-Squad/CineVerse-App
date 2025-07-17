package com.giraffe.imageviewer.di


import com.giraffe.imageviewer.mlmodel.SafeIslamicImageClassifier
import com.giraffe.imageviewer.mlmodel.SafeIslamicImageClassifierImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val imageViewerModule = module {
    single<SafeIslamicImageClassifier> {
        SafeIslamicImageClassifierImpl(androidContext())
    }
}
