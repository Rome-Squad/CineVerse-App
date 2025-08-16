plugins {
    `kotlin-dsl`
}

group = "com.giraffe.convention"

dependencies {
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.android.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationConvention") {
            id = libs.plugins.cineVerse.android.application.get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibraryConvention") {
            id = libs.plugins.cineVerse.android.library.get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("KotlinLibraryConventionPlugin") {
            id = libs.plugins.cineVerse.kotlin.library.get().pluginId
            implementationClass = "KotlinLibraryConventionPlugin"
        }

        register("androidComposeModuleLibraryConvention") {
            id = libs.plugins.cineVerse.android.compose.module.get().pluginId
            implementationClass = "AndroidComposeModuleLibraryConventionPlugin"
        }
    }
}