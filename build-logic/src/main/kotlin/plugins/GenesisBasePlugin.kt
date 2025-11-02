package plugins

// Add these essential imports

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.Actions.with


/**
 * Base convention plugin for Android modules.
 * Configures JVM target, Java toolchain, and other shared settings.
 */
class GenesisAndroidBasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        fun apply(target: Project) {
            with(target) {
                with(target.pluginManager) {
                    apply("org.jetbrains.kotlin.android")
                    apply("org.jetbrains.kotlin.jvm")
                }
            }
        }
    }
}
