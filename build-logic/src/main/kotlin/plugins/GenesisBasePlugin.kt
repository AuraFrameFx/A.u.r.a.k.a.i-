package plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class GenesisBasePlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        plugins.withId("com.android.application") {
            extensions.getByType(ApplicationExtension::class.java).apply {
                compileSdk = 36
                defaultConfig { minSdk = 33 }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_21
                    targetCompatibility = JavaVersion.VERSION_21
                }
                buildFeatures { compose = true }
            }
        }
        plugins.withId("com.android.library") {
            extensions.getByType(LibraryExtension::class.java).apply {
                compileSdk = 36
                defaultConfig { minSdk = 33 }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_21
                    targetCompatibility = JavaVersion.VERSION_21
                }
                buildFeatures { compose = true }
            }
        }
    }
}
