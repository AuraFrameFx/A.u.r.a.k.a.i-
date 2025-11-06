plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    // Plugin dependencies for convention plugins
    // These allow the convention plugins to apply Android, Kotlin, Hilt, and KSP plugins
    implementation(libs.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.hilt.gradle.plugin)
    implementation(libs.ksp.gradle.plugin)
}

// ═══════════════════════════════════════════════════════════════════════════
// Precompiled Script Plugins
// ═══════════════════════════════════════════════════════════════════════════
//
// All convention plugins are now precompiled script plugins (.gradle.kts files).
// They are automatically registered by Gradle - no manual registration needed!
//
// The file name becomes the plugin ID:
//   genesis.android.application.gradle.kts → id("genesis.android.application")
//
// Available Plugins:
//
//   FOUNDATIONAL:
//   • genesis.android.base        - Base Android configuration (SDK, Kotlin, deps)
//
//   APPLICATION & LIBRARY:
//   • genesis.android.application - For the :app module (includes Hilt, Compose, Services)
//   • genesis.android.library     - For Android library modules
//   • genesis.kotlin.jvm          - For pure Kotlin JVM modules (no Android)
//
//   COMPOSABLE (mix and match):
//   • genesis.android.hilt        - Hilt dependency injection
//   • genesis.android.compose     - Jetpack Compose UI
//   • genesis.android.room        - Room Database
//   • genesis.android.yukihook    - YukiHook/Xposed framework
//
// Example Usage:
//   plugins {
//       id("genesis.android.library")  // Base library
//       id("genesis.android.compose")  // Add Compose UI
//       id("genesis.android.hilt")     // Add Hilt DI
//   }
//
// ═══════════════════════════════════════════════════════════════════════════
