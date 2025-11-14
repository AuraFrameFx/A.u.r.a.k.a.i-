plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.aurakai.auraframefx.core.common"
    compileSdk = libs.versions.compile.sdk.get().toInt()
}
dependencies {
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

// Compile-only for Xposed API (no runtime bundling)
}
