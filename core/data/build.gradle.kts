plugins {
    id("genesis.android.application")
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.21"
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}


android {
    namespace = "dev.aurakai.auraframefx.core.data"
    compileSdk = 36

}

dependencies {
    // Expose domain layer
    api(project(":core:domain"))

    // Data layer dependencies
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // Hilt - required when alias(libs.plugins.hilt) is applied
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Common utilities
    implementation(project(":core:common"))

    // Testing
}
