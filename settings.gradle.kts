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
include(":presentation:onboarding")
include(":designsystem")
include(":imageviewer")
include(":datasource:remote:user")
include(":datasource:local:movie")
include(":repository:user")
include(":repository:movie")
include(":repository:series")
include(":datasource:remote:movie")
include(":datasource:remote:series")
include(":datasource:local:user")
include(":datasource:local:series")
include(":datasource:local:person")
include(":repository:person")
include(":datasource:remote:person")
include(":repository:review")
include(":datasource:remote:review")
include(":datasource:local:review")
include(":repository:explore")
include(":datasource:local:explore")
include(":datasource:remote:explore")
include(":domain:media")
