// Root build.gradle.kts
// ═══════════════════════════════════════════════════════════════════════════
// A.u.r.a.K.a.I Reactive Intelligence - Root Build Configuration
// ═══════════════════════════════════════════════════════════════════════════
//
// This is the root build file for the entire multi-module project.
// Its ONLY purpose is to make plugins available on the classpath.
//
// ALL configuration logic has been moved to convention plugins in build-logic/
// This follows modern Gradle best practices for large multi-module projects.
//
// Convention Plugins Available:
//   • genesis.android.application - For the :app module
//   • genesis.android.library     - For Android library modules
//   • genesis.android.base        - Foundational Android configuration
//   • genesis.android.hilt        - Hilt dependency injection
//   • genesis.android.compose     - Jetpack Compose UI
//   • genesis.android.room        - Room Database
//   • genesis.android.yukihook    - YukiHook/Xposed framework
//   • genesis.kotlin.jvm          - Pure Kotlin JVM modules
//
// ═══════════════════════════════════════════════════════════════════════════

plugins {
    // CRITICAL HILT/KSP WORKAROUND:
    // Apply plugins with `apply false` to make them available on the classpath
    // for ALL subprojects. This prevents Hilt/KSP resolution errors in multi-module projects.
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.compose.compiler) apply false
}

// Root-level clean task
tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

