import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            extensions.configure<ApplicationExtension> {
                compileSdk = ProjectConfig.compileSdk

                defaultConfig {
                    applicationId = "com.giraffe.cineverseapp"
                    minSdk = ProjectConfig.minSdk
                    targetSdk = ProjectConfig.targetSdk
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    versionName = ProjectConfig.versionName
                    versionCode = ProjectConfig.versionCode
                }

                buildTypes {
                    getByName("release").apply {
                        isMinifyEnabled = true
                        isShrinkResources = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }

                    create("publicTest") {
                        initWith(getByName("debug"))
                        manifestPlaceholders["isFirebaseDeactivated"] = true
                    }
                }

                compileOptions {
                    sourceCompatibility = ProjectConfig.javaVersion
                    targetCompatibility = ProjectConfig.javaVersion
                }

                buildFeatures.apply {
                    compose = true
                    buildConfig = true
                    viewBinding = false
                    dataBinding = false
                    mlModelBinding = true
                    aidl = false
                    prefab = false
                    renderScript = false
                    shaders = false
                }
            }
            configureKotlinCompiler(ProjectConfig.jvmTarget)
        }
    }
}

