plugins {
    `kotlin-dsl`        // applies java-gradle-plugin
}

repositories { google(); mavenCentral() }

dependencies {
    // Use your catalog aliases if present; direct GAVs are fine too
    implementation("com.android.tools.build:gradle:9.0.0-alpha13")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.0-Beta3")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.57.2")
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.3.1")
    implementation("com.google.gms:google-services:4.4.2")
}

gradlePlugin {
    plugins {
        register("genesis.android.base") {
            id = "genesis.android.base"
            implementationClass = "plugins.GenesisBasePlugin"
        }
        register("genesis.android.library") {
            id = "genesis.android.library"
            implementationClass = "plugins.GenesisLibraryPlugin"
        }
        register("genesis.android.application") {
            id = "genesis.android.application"
            implementationClass = "plugins.GenesisApplicationPlugin"
        }
    }
}
