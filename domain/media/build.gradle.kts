plugins {
    //noinspection JavaPluginLanguageLevel
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.cineVerse.kotlin.library)
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(projects.domain.user)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
}