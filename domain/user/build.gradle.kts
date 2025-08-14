plugins {
    //noinspection JavaPluginLanguageLevel
    `java-library`
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.cineVerse.kotlin.library)
    alias(libs.plugins.ksp)
}

coverageConfig {
    includes = listOf("**/usecase/**")
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
}