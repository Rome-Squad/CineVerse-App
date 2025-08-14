import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
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
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        val versionCatalog = rootProject.extensions
            .getByType<VersionCatalogsExtension>()
            .named("libs")

        val requiredJvmTarget = versionCatalog.findVersion("jvmTarget").get().toString()
        val javaVersion =
            JavaVersion.toVersion(versionCatalog.findVersion("javaVersion").get().toString())

        pluginManager.apply {
            apply(versionCatalog.findPlugin("jetbrains.kotlin.jvm").get().get().pluginId)
            apply("jacoco")
        }

        applyDependencies(versionCatalog)

        extensions.configure<KotlinJvmProjectExtension> {
            compilerOptions {
                jvmTarget.set(JvmTarget.valueOf(requiredJvmTarget))
            }
        }

        extensions.getByType<JavaPluginExtension>().apply {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
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

    private fun Project.applyDependencies(versionCatalog: VersionCatalog) {
        dependencies.apply {
            add("testImplementation", versionCatalog.findBundle("unit.test").get())
            add("testImplementation", project.dependencies.kotlin("test"))
        }
    }
}