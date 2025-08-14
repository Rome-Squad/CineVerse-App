import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        pluginManager.apply {
            apply(libs.plugins.jetbrains.kotlin.jvm.get().pluginId)
            apply("jacoco")
        }

        applyDependencies()

        extensions.configure<KotlinJvmProjectExtension> {
            compilerOptions {
                jvmTarget.set(ProjectConfig.jvmTarget)
            }
        }

        extensions.getByType<JavaPluginExtension>().apply {
            sourceCompatibility = ProjectConfig.javaVersion
            targetCompatibility = ProjectConfig.javaVersion
        }

        extensions.configure<JacocoPluginExtension>() {
            toolVersion = "0.8.13"
        }

        val jacocoReportType = tasks.withType<JacocoReport>()
        val testType = tasks.withType<Test> {
            useJUnitPlatform()
            testLogging {
                showStandardStreams = true
            }
            finalizedBy(jacocoReportType)
        }

        tasks.withType<JacocoReport> {
            dependsOn(testType)
            reports {
                xml.required.set(true)
                csv.required.set(true)
                html.required.set(true) // Added HTML report
            }
            doLast {
                println("Coverage report: file://${layout.buildDirectory}/reports/jacoco/test/html/index.html") // Adds clickable report link after test
            }
        }

        tasks.withType<JacocoCoverageVerification>() {
            dependsOn("org.gradle.api.tasks.testing")
            violationRules {
                classDirectories.setFrom(
                    classDirectories.files.map {
                        fileTree(it) {
                            include("**/explore/usecase/**")
                            include("**/movies/usecase/**")
                            include("**/series/usecase/**")
                            include("**/person/usecase/**")
                            exclude("**/explore/usecase/ExploreUseCases.class")
                            exclude("**/movies/usecase/MoviesUseCases.class")
                        }
                    }
                )

                rule {
                    limit {
                        minimum = "0.8".toBigDecimal()
                    }
                }
                rule {
                    limit {
                        counter = "LINE"
                        value = "COVEREDRATIO"
                        minimum = "0.8".toBigDecimal()
                    }
                }
                rule {
                    limit {
                        counter = "BRANCH"
                        value = "COVEREDRATIO"
                        minimum = "0.8".toBigDecimal()
                    }
                }
                rule {
                    limit {
                        counter = "METHOD"
                        value = "COVEREDRATIO"
                        minimum = "0.8".toBigDecimal()
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