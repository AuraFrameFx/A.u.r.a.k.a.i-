plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}



android {
    namespace = "dev.aurakai.auraframefx.datavein.oracle"
    compileSdk = libs.versions.compile.sdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
    }

    dependencies {
        // Include local libs directory for compileOnly dependencies
        compileOnly(files("$projectDir/libs/api-82.jar"))
        compileOnly(files("$projectDir/libs/api-82-sources.jar"))

        // Libsu for root operations
        implementation(libs.libsu.core)
        implementation(libs.libsu.io)
        implementation(libs.libsu.service)
        api(libs.androidx.core.ktx) // if APIs leak types
        implementation(libs.androidx.appcompat)
        implementation(libs.timber)

        // If this library uses Compose UI:
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.compose.ui)
        implementation(libs.compose.material3)

        // Hilt in library
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)

        // Xposed API and other compile-only dependencies
    }
}
