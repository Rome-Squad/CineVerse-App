plugins {
    //noinspection JavaPluginLanguageLevel
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.cineVerse.kotlin.library)
    alias(libs.plugins.ksp)
}

coverageConfig {
    includes = listOf(
        "**/explore/usecase/**",
        "**/movies/usecase/**",
        "**/series/usecase/**",
        "**/person/usecase/**"
    )
    excludes = listOf(
        "**/explore/usecase/ExploreUseCases.class",
        "**/movies/usecase/MoviesUseCases.class"
    )
}

dependencies {
    implementation(projects.domain.user)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
}