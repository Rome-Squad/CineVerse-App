import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.google.firebase.firebase.perf)
    alias(libs.plugins.ksp)
}

fun getSecret(key: String): String {
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


android {
    namespace = "com.giraffe.cineverseapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.giraffe.cineverseapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "API_KEY", "\"${getSecret("API_KEY")}\"")
            buildConfigField("String", "BASE_URL", "\"${getSecret("BASE_URL")}\"")
            buildConfigField("String", "ACCESS_TOKEN", "\"${getSecret("ACCESS_TOKEN")}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {

    implementation(project(":presentation:authentication"))
    implementation(project(":presentation:home"))
    implementation(project(":presentation:details"))
    implementation(project(":presentation:explore"))
    implementation(project(":presentation:match"))
    implementation(project(":presentation:profile"))
    implementation(project(":presentation:onboarding"))

    implementation(project(":designsystem"))
    implementation(project(":imageviewer"))

    implementation(project(":domain:user"))
    implementation(project(":domain:movies"))
    implementation(project(":domain:series"))
    implementation(project(":domain:review"))
    implementation(project(":domain:person"))
    implementation(project(":domain:explore"))

    implementation(project(":repository:user"))
    implementation(project(":repository:movie"))
    implementation(project(":repository:series"))
    implementation(project(":repository:person"))
    implementation(project(":repository:review"))
    implementation(project(":repository:explore"))

    implementation(project(":datasource:remote:user"))
    implementation(project(":datasource:remote:movie"))
    implementation(project(":datasource:remote:series"))
    implementation(project(":datasource:remote:person"))
    implementation(project(":datasource:remote:review"))
    implementation(project(":datasource:remote:explore"))

    implementation(project(":datasource:local:user"))
    implementation(project(":datasource:local:movie"))
    implementation(project(":datasource:local:series"))
    implementation(project(":datasource:local:person"))
    implementation(project(":datasource:local:review"))
    implementation(project(":datasource:local:explore"))

    
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    //datastore
    implementation(libs.datastore.preferences)

    //koin
    implementation(libs.koin.androidx.compose)

    //serialization
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)

    //room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)

    //ktor
    implementation(libs.bundles.ktor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}