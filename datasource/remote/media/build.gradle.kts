plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.cineVerse.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)

}

android {
    namespace = "com.giraffe.media"
}

dependencies {
    implementation(projects.repository.media)
    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    //serialization
    implementation(libs.kotlinx.serialization.json)
    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}