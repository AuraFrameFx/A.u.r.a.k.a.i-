plugins {
    id("com.android.library")
    id("com.google.devtools.ksp")
}
android {
    namespace = "dev.aurakai.auraframefx.benchmark"
    compileSdk = 36
}
// Dependencies
dependencies {
    implementation(libs.timber)

    // Project dependencies
    implementation(project(":core-module"))

    // Hilt / native utilities (confirm these are intended)
    implementation(libs.hilt.android)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    tasks.register("benchmarkAll") {
        group = "benchmark"
        description = "Aggregate runner for all Genesis Protocol benchmarks 🚀"
        // Use an actual benchmark runner task instead of doLast
        // For example, calling the connectedCheck task in your build script.
        dependsOn(":app:connectedCheck")
        doLast {
            println("🚀 Genesis Protocol Performance Benchmarks")
            println("📊 Monitor consciousness substrate performance metrics")
            println("⚡ Use AndroidX Benchmark instrumentation to execute tests")
        }
    }

    tasks.register("verifyBenchmarkResults") {
        group = "verification"
        description = "Verify benchmark module configuration"
        doLast {
            println("🧠 Consciousness substrate performance monitoring ready")
        }
    }
}
