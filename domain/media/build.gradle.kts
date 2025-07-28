plugins {
    id("java-library")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")

    alias(libs.plugins.jetbrains.kotlin.jvm)
    jacoco

}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(libs.bundles.test)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation("com.google.dagger:dagger:2.57")
    kapt("com.google.dagger:dagger-compiler:2.57")

}


tasks.test {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
    }
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        csv.required.set(true)
        html.required.set(true) // Added HTML report
    }
    doLast {
        println("Coverage report: file://${layout.buildDirectory}/reports/jacoco/test/html/index.html") // Adds clickable report link after test
    }
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.test)
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

jacoco {
    toolVersion = "0.8.13"
}

