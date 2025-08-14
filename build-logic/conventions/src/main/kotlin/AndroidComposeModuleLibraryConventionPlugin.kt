import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType

class AndroidComposeModuleLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply {
                apply(libs.plugins.android.library.get().pluginId)
                apply(libs.plugins.kotlin.android.get().pluginId)
                apply(libs.plugins.kotlin.compose.get().pluginId)
                apply(libs.plugins.ksp.get().pluginId)
            }

            configureAndroidLibrary(
                testRunner = "androidx.test.runner.AndroidJUnitRunner",
                isComposeLibrary = true
            )

            applyDependencies()
            tasks.withType<Test>().configureEach { useJUnitPlatform() }
        }
    }

    private fun Project.applyDependencies() {
        dependencies.apply {
            add("implementation", libs.bundles.android.presentation)
            add("implementation", libs.bundles.compose)
            add("implementation", libs.bundles.di)
            add("implementation", platform(libs.androidx.compose.bom))
            add("ksp", libs.hilt.compiler)
            add("testImplementation", libs.bundles.unit.test)
        }
    }
}