import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.withType

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val versionCatalog = project.rootProject
            .extensions
            .getByType<VersionCatalogsExtension>()
            .named("libs")

        with(project) {
            configureAndroidLibrary(versionCatalog = versionCatalog)
            applyPlugins(versionCatalog)
            applyDependencies(versionCatalog)
            tasks.withType<Test>().configureEach { useJUnitPlatform() }
        }
    }

    private fun Project.applyPlugins(versionCatalog: VersionCatalog) {
        pluginManager.apply {
            apply(versionCatalog.findPlugin("android.library").get().get().pluginId)
            apply(versionCatalog.findPlugin("kotlin.android").get().get().pluginId)
        }
    }

    private fun Project.applyDependencies(versionCatalog: VersionCatalog) {
        dependencies.apply {
            add("implementation", versionCatalog.findLibrary("androidx.core.ktx").get())
            add("testImplementation", versionCatalog.findBundle("unit.test").get())
            add("androidTestImplementation", versionCatalog.findLibrary("androidx.junit").get())
            add("testImplementation", dependencies.kotlin("test"))
        }
    }
}