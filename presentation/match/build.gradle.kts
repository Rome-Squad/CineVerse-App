plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.cineVerse.android.compose.module)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.giraffe.presentation.match"
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.imageviewer)
    implementation(projects.domain.media)
    implementation(projects.domain.user)
    implementation(projects.api.match)
    implementation(projects.api.details)
    implementation(projects.api.authentication)
    //android youtube player
    implementation(libs.core)
    // serialization
    implementation(libs.kotlinx.serialization.json)
}