plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.cineVerse.android.compose.module)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.giraffe.presentation.profile"
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.imageviewer)
    implementation(projects.domain.media)
    implementation(projects.domain.user)
    implementation(projects.api.profile)
    implementation(projects.api.authentication)
    implementation(projects.api.explore)
    implementation(projects.api.details)
    implementation(projects.api.home)
    //webview
    implementation(libs.accompanist.webview)
}