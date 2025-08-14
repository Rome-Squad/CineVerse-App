plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.cineVerse.android.compose.module)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.giraffe.presentation.details"
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.imageviewer)
    implementation(projects.domain.media)
    implementation(projects.domain.user)
    implementation(projects.api.details)
    implementation(projects.api.authentication)
    //android youtube player
    implementation(libs.core)
    //pagination
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
}