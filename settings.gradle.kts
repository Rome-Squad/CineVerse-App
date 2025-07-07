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
include(":presentation:onboarding")
include(":designsystem")
include(":datasource:remote:user")
include(":datasource:local:movie")
include(":repository:user")
include(":repository:movie")
include(":repository:series")
include(":datasource:remote:movie")
include(":datasource:remote:series")
include(":datasource:local:user")
include(":datasource:local:series")
include(":domain:review")
include(":domain:person")
include(":datasource:local:person")
include(":repository:person")
include(":datasource:remote:person")
include(":repository:review")
