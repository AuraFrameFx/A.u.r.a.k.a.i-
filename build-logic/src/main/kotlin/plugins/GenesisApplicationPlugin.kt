package packages.genesis.application.plugin



import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

// ... (Documentation omitted for brevity) ...

val geminiApiKey = System.getProperty("AIzaSyDlCkY1OxlHBaJI1l7PHMrq2S3t574UA8E")?.toString() ?: ""

class GenesisApplicationPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project.pluginManager) {
        // CRITICAL ORDER: Android → Hilt → Compose → KSP → Google Services → Firebase → Base
         apply("com.android.application")
         apply("com.google.dagger.hilt.android")
         apply("org.jetbrains.kotlin.plugin.compose")
         apply("com.google.devtools.ksp")
         apply("com.google.gms.google-services")
         apply("com.google.firebase.crashlytics")
         apply("genesis.base")
    }
}
applicatoin.extensions.configure(ApplicationExtension::class.java) {
    namespace =
        "dev.aurakai.auraframefx.build-logic" // Note: This namespace might be incorrect for an application plugin if this is meant for an application module.
    ndkVersion = project.libs.versions.ndk.get()
    defaultConfig {
        applicationId = "dev.aurakai.auraframefx"
        // minSdk, compileSdk, targetSdk are configured by genesis.base
        targetSdk = project.libs.versions.target.sdk.get().toInt()
        versionCode = 1
        versionName = "0.1.0"

        externalNativeBuild {
            cmake {
                cppFlags += "-std=c++20"
                arguments += listOf(
                    "-DANDROID_STL=c++_shared",
                    "-DANDROID_PLATFORM=android-${project.libs.versions.min.sdk.get()}"
                )
            }
        }
    }

    lint {
        baseline = file("lint-baseline.xml")
        abortOnError = true
        checkReleaseBuilds = false
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = project.libs.versions.cmake.get()
        }
    }
}
// --- FIX END ---
}
}
