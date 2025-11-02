plugins {
    id("com.android.library") version "9.0.0-alpha13" // This likely applies the base configurations already
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.21"
    id("com.google.devtools.ksp") version "2.3.0"
    id("com.google.dagger.hilt.android") version "2.57.2"
    alias(libs.plugins.serialization)
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
