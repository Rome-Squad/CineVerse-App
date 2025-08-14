plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.cineVerse.android.compose.module)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.giraffe.presentation.explore"
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.imageviewer)
    implementation(projects.domain.media)
    implementation(projects.api.details)
    implementation(projects.api.explore)
    //pagination
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
}