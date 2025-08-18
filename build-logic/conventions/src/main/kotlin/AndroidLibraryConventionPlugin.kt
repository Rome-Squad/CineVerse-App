import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.withType

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            configureAndroidLibrary()
            applyPlugins()
            applyDependencies()
            tasks.withType<Test>().configureEach { useJUnitPlatform() }


        }
    }

    private fun Project.applyPlugins() {
        pluginManager.apply {
            apply(libs.plugins.android.library.get().pluginId)
            apply(libs.plugins.kotlin.android.get().pluginId)
        }
    }

    private fun Project.applyDependencies() {
        dependencies.apply {
            add("implementation", libs.androidx.core.ktx)
            add("testImplementation", libs.bundles.unit.test)
            add("androidTestImplementation", libs.androidx.junit)
            add("testImplementation", dependencies.kotlin("test"))
        }
    }
}