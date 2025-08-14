import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        pluginManager.apply {
            apply("java-library")
            apply("jacoco")
            apply(libs.plugins.jetbrains.kotlin.jvm.get().pluginId)
        }

        applyDependencies()

        extensions.configure<JavaPluginExtension> {
            sourceCompatibility = ProjectConfig.javaVersion
            targetCompatibility = ProjectConfig.javaVersion
        }

        extensions.configure<KotlinJvmProjectExtension> {
            compilerOptions.jvmTarget.set(ProjectConfig.jvmTarget)
        }

        extensions.configure<JacocoPluginExtension> {
            toolVersion = "0.8.13"
        }

        tasks.withType<Test> {
            useJUnitPlatform()
            testLogging { showStandardStreams = true }
            finalizedBy("jacocoTestReport")
        }

        tasks.withType<JacocoReport> {
            dependsOn("test")
            reports {
                xml.required.set(true)
                csv.required.set(true)
                html.required.set(true)
            }
            doLast {
                println("Coverage report: file://${layout.buildDirectory.get()}/reports/jacoco/test/html/index.html")
            }
        }

        tasks.withType<JacocoCoverageVerification> {
            dependsOn("test")
            violationRules {
                listOf(
                    Triple(null, null, "0.8"),
                    Triple("LINE", "COVEREDRATIO", "0.8"),
                    Triple("BRANCH", "COVEREDRATIO", "0.8"),
                    Triple("METHOD", "COVEREDRATIO", "0.8")
                ).forEach { (counter, value, min) ->
                    rule {
                        limit {
                            counter?.let { this.counter = it }
                            value?.let { this.value = it }
                            minimum = min.toBigDecimal()
                        }
                    }
                }
            }
        }
    }

    private fun Project.applyDependencies() {
        dependencies.apply {
            add("testImplementation", libs.bundles.unit.test)
            add("testImplementation", project.dependencies.kotlin("test"))
        }
    }
}