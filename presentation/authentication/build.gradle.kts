
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.cineVerse.android.compose.module)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.giraffe.presentation.authentication"
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.imageviewer)
    implementation(projects.api.authentication)
    implementation(projects.domain.user)
    implementation(projects.api.home)
    //webview
    implementation(libs.accompanist.webview)
}