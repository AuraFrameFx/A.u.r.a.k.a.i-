
plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        // UPDATED: Java 25 toolchain for development environment
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

// These blocks MUST be at the top level, AFTER the java block.
// IMPORTANT: build-logic cannot use version catalog (libs.*) - it builds BEFORE catalog is available!
dependencies {
    implementation("com.android.tools.build:gradle:9.0.0-alpha13")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.0-Beta2")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.57.2")
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.3.1")
    implementation("com.google.gms:google-services:4.4.4")

    testImplementation(gradleTestKit())
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.0.1")
}
// ═══════════════════════════════════════════════════════════════════════════
// Binary Kotlin Class Plugins Registration
// ═══════════════════════════════════════════════════════════════════════════
//
// These are the PRIMARY convention plugins that modules should use.
// They are Kotlin class plugins (not precompiled scripts) for maximum control
// over plugin application order.
//
gradlePlugin {
    plugins {
        // Primary application plugin - for :app module
        register("genesisAndroidApplication") {
            id = "genesis.android.application"
            implementationClass = "plugins.GenesisApplicationPlugin"
            displayName = "Genesis Android Application Plugin"
            description = "Applies Android Application plugin with Hilt, KSP, Compose, Serialization, and Firebase"
        }

        // Primary library plugin - for all library modules
        register("genesisAndroidLibrary") {
            id = "genesis.android.library"
            implementationClass = "plugins.GenesisLibraryPlugin"
            displayName = "Genesis Android Library Plugin"
            description = "Applies Android Library plugin with Hilt, Compose, and KSP"
        }

        // Base configuration plugin - applied automatically by application and library plugins
        register("genesisAndroidBase") {
            id = "genesis.android.base"
            implementationClass = "plugins.GenesisBasePlugin"
            displayName = "Genesis Android Base Plugin"
            description = "Foundational configuration for all Android modules (SDK versions, Kotlin, dependencies)"
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════
// CORRECT USAGE EXAMPLES
// ═══════════════════════════════════════════════════════════════════════════
//
// For :app module:
//   plugins {
//       id("genesis.android.application")  // All-in-one: Android, Hilt, KSP, Compose, Serialization, Firebase
//   }
//
// For standard library module:
//   plugins {
//       id("genesis.android.library")  // All-in-one: Android, Hilt, Compose, KSP
//   }
//
// For YukiHook/Xposed module:
//   plugins {
//       id("genesis.android.library")   // Base library with Hilt, Compose, KSP
//       id("genesis.android.yukihook")  // Add YukiHook/Xposed support
//   }
//
// For Room database module:
//   plugins {
//       id("genesis.android.library")  // Base library
//       id("genesis.android.room")     // Add Room Database
//   }
//
// ═══════════════════════════════════════════════════════════════════════════
