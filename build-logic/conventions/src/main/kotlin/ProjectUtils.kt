import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

fun Project.configureAndroidLibrary(
    testRunner: String = "androidx.test.runner.AndroidJUnitRunner",
    isComposeLibrary: Boolean = false
) {
    extensions.configure<LibraryExtension> {
        compileSdk = ProjectConfig.compileSdk

        defaultConfig {
            minSdk = ProjectConfig.minSdk
            testOptions.targetSdk = ProjectConfig.targetSdk
            testInstrumentationRunner = testRunner
            consumerProguardFiles("consumer-rules.pro")
        }

        buildTypes {
            getByName("debug").apply {
                isMinifyEnabled = false
                isShrinkResources = false
            }
            getByName("release").apply {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            create("publicTest") {
                initWith(getByName("debug"))
            }
        }

        compileOptions {
            sourceCompatibility = ProjectConfig.javaVersion
            targetCompatibility = ProjectConfig.javaVersion
        }

        if (isComposeLibrary) {
            buildFeatures {
                compose = true
            }
        }
    }

    configureKotlinCompiler(ProjectConfig.jvmTarget)
}


fun Project.configureKotlinCompiler(target: JvmTarget) {
    extensions.configure<KotlinAndroidProjectExtension> {
        compilerOptions {
            jvmTarget.set(target)
        }
    }
}