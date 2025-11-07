package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class GenesisApplicationPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project.pluginManager) {
        // Order: Android → Hilt → Compose → KSP → (Google Services opt) → Base
        apply("com.android.application")
        apply("com.google.dagger.hilt.android")
        apply("org.jetbrains.kotlin.plugin.compose")
        apply("com.google.devtools.ksp")
        // apply("com.google.gms.google-services") // enable only if app needs it
        apply("genesis.android.base")
    }
}
