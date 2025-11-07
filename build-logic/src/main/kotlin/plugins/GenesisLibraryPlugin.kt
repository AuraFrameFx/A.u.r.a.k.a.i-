package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class GenesisLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project.pluginManager) {
        // Order: Android → Hilt → Compose → KSP → Base
        apply("com.android.library")
        apply("com.google.dagger.hilt.android")
        apply("org.jetbrains.kotlin.plugin.compose")
        apply("com.google.devtools.ksp")
        apply("genesis.android.base")
    }
}
