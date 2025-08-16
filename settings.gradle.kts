pluginManagement {
    repositories {
        google()
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

includeBuild("build-logic")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "CineVerseApp"
include(":app")
include(":presentation:authentication")
include(":presentation:home")
include(":presentation:details")
include(":presentation:explore")
include(":presentation:match")
include(":presentation:profile")
include(":domain:user")
include(":designsystem")
include(":imageviewer")
include(":datasource:remote:user")
include(":repository:user")
include(":datasource:local:user")
include(":repository:media")
include(":domain:media")
include(":datasource:local:media")
include(":datasource:remote:media")
include(":api:details")
include(":api:explore")
include(":api:profile")
include(":api:authentication")
include(":api:home")
include(":api:match")