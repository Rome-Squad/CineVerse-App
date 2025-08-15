plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.cineVerse.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.giraffe.media"
}

dependencies {
    implementation(projects.repository.media)
    implementation(libs.kotlinx.datetime)
    //room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)
    //serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}