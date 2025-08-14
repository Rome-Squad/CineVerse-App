import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object ProjectConfig {
    const val compileSdk = 36
    const val minSdk = 24
    const val targetSdk = 36
    val jvmTarget = JvmTarget.JVM_11
    val javaVersion = JavaVersion.VERSION_11
    val versionName = "1.0.1"
    val versionCode = 1
}