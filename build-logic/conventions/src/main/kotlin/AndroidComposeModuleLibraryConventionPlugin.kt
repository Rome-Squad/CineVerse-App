import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType

class AndroidComposeModuleLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val versionCatalog = project.extensions.getByType<VersionCatalogsExtension>().named("libs")
        with(project) {
            pluginManager.apply {
                apply(versionCatalog.findPlugin("android.library").get().get().pluginId)
                apply(versionCatalog.findPlugin("kotlin.android").get().get().pluginId)
                apply(versionCatalog.findPlugin("kotlin.compose").get().get().pluginId)
                apply(versionCatalog.findPlugin("ksp").get().get().pluginId)
            }

            configureAndroidLibrary(
                testRunner = "androidx.test.runner.AndroidJUnitRunner",
                versionCatalog = versionCatalog,
                isComposeLibrary = true
            )

            applyDependencies(versionCatalog)
            tasks.withType<Test>().configureEach { useJUnitPlatform() }
        }
    }

    private fun Project.applyDependencies(versionCatalog: VersionCatalog) {
        dependencies.apply {
            add("implementation", versionCatalog.findBundle("android.presentation").get())
            add("implementation", versionCatalog.findBundle("compose").get())
            add("implementation", versionCatalog.findBundle("di").get())
            add(
                "implementation",
                platform(versionCatalog.findLibrary("androidx.compose.bom").get()).get()
            )
            add("ksp", versionCatalog.findLibrary("hilt.compiler").get())
            add("testImplementation", versionCatalog.findBundle("unit.test").get())
        }
    }
}