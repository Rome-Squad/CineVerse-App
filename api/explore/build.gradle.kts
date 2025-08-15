plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.cineVerse.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.giraffe.api.explore"
}

dependencies {
    implementation(libs.androidx.compose.foundation.foundation)
}