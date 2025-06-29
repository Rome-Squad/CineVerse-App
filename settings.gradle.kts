pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CineVerse App"
include(":app")
include(":presentation:authentication")
include(":presentation:home")
include(":presentation:details")
include(":presentation:explore")
include(":presentation:match")
include(":presentation:profile")
include(":domain:user")
include(":domain:movies")
include(":domain:series")
include(":common:domain")
include(":data:user:datasource:local")
include(":data:user:datasource:remote")
include(":data:user:repository")
include(":data:movies:datasource:local")
include(":data:movies:datasource:remote")
include(":data:movies:repository")
include(":data:series:datasource:local")
include(":data:series:datasource:remote")
include(":data:series:repository")
include(":common:data:datasource:local")
include(":common:data:datasource:remote")
include(":common:presentation")
include(":common:presentation:composable")
include(":presentation:onboarding")
