import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            val libs = rootProject
                .extensions
                .getByType<VersionCatalogsExtension>()
                .named("libs")

            pluginManager.apply {
                apply(libs.findPlugin("android.application").get().get().pluginId)
                apply(libs.findPlugin("kotlin.android").get().get().pluginId)
                apply(libs.findPlugin("kotlin.compose").get().get().pluginId)
            }

            val jvmTarget = libs.findVersion("jvmTarget").get().toString()

            val javaVersion =
                JavaVersion.toVersion(libs.findVersion("javaVersion").get().toString())

            extensions.configure<ApplicationExtension> {
                compileSdk = libs.findVersion("compileSdk").get().toString().toInt()

                defaultConfig {
                    applicationId = libs.findVersion("applicationId").get().toString()
                    minSdk = libs.findVersion("minSdk").get().toString().toInt()
                    targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
                    testInstrumentationRunner = libs.findVersion("testRunner").get().toString()
                    versionName = libs.findVersion("versionName").get().toString()
                    versionCode = libs.findVersion("versionCode").get().toString().toInt()
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
                }

                compileOptions {
                    sourceCompatibility = javaVersion
                    targetCompatibility = javaVersion
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
            configureKotlinCompiler(jvmTarget)
        }
    }
}

