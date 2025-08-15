import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.google.firebase.firebase.perf)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.cineVerse.android.application)
}

android {
    namespace = "com.giraffe.cineverseapp"

    defaultConfig {
        buildConfigField("String", "API_KEY", "\"${getSecret("API_KEY")}\"")
        buildConfigField("String", "BASE_URL", "\"${getSecret("BASE_URL")}\"")
        buildConfigField("String", "ACCESS_TOKEN", "\"${getSecret("ACCESS_TOKEN")}\"")
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_KEY", "\"${getSecret("API_KEY")}\"")
            buildConfigField("String", "BASE_URL", "\"${getSecret("BASE_URL")}\"")
            buildConfigField("String", "ACCESS_TOKEN", "\"${getSecret("ACCESS_TOKEN")}\"")
        }

        release {
            buildConfigField("String", "API_KEY", "\"${getSecret("API_KEY")}\"")
            buildConfigField("String", "BASE_URL", "\"${getSecret("BASE_URL")}\"")
            buildConfigField("String", "ACCESS_TOKEN", "\"${getSecret("ACCESS_TOKEN")}\"")
        }
    }

    bundle {
        language {
            enableSplit = true
        }
    }
}

dependencies {
    projectModules()
    networkDependencies()
    localDatabaseDependencies()
    hiltDiDependencies()
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    //firebase
    releaseImplementation(libs.bundles.firebase)
    //data store
    implementation(libs.datastore.preferences)
    //serialization
    implementation(libs.kotlinx.serialization.json)
    //worker
    implementation(libs.androidx.work.runtime.ktx)
    //splash screen api
    implementation(libs.androidx.core.splashscreen)
}

private fun getSecret(key: String): String {
    val secretsProps = File(rootDir, "app/secrets.properties")
    if (!secretsProps.exists()) {
        throw GradleException("Missing secrets.properties file at app/secrets.properties")
    }

    val props = Properties().apply {
        load(secretsProps.inputStream())
    }

    return props[key]?.toString()
        ?: throw GradleException("Missing required secret key: $key in secrets.properties")
}

private fun DependencyHandlerScope.projectModules() {
    implementation(projects.presentation.home)
    implementation(projects.presentation.authentication)
    implementation(projects.presentation.details)
    implementation(projects.presentation.explore)
    implementation(projects.presentation.match)
    implementation(projects.presentation.profile)
    implementation(projects.designsystem)
    implementation(projects.imageviewer)
    implementation(projects.domain.user)
    implementation(projects.domain.media)
    implementation(projects.repository.user)
    implementation(projects.repository.media)
    implementation(projects.datasource.remote.user)
    implementation(projects.datasource.remote.media)
    implementation(projects.datasource.local.user)
    implementation(projects.datasource.local.media)
    implementation(projects.api.details)
    implementation(projects.api.explore)
    implementation(projects.api.home)
    implementation(projects.api.match)
    implementation(projects.api.profile)
    implementation(projects.api.authentication)
}

private fun DependencyHandlerScope.networkDependencies() {
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
}

private fun DependencyHandlerScope.localDatabaseDependencies() {
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)
}

private fun DependencyHandlerScope.hiltDiDependencies() {
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.work)
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.hilt.compiler)
}