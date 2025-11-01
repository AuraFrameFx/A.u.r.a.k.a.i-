plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)

}

android {
    namespace = "dev.aurakai.auraframefx.coremodule"
    compileSdk = 36

}

dependencies {

    // Module dependency
    api(project(":list"))

    // Concurrency and serialization
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.coroutines)
    implementation(libs.kotlinx.serialization.json)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    // File operations and compression
    implementation(libs.commons.io)
    implementation(libs.commons.compress)
    implementation(libs.libsu.io)
    implementation(libs.libsu.core)

    // Hooking/runtime-only compile-time APIs for modules that interact with Xposed/YukiHook
    compileOnly(libs.yukihookapi)
    compileOnly(libs.xposed.api)
    dependencies {
        implementation(libs.androidx.appcompat)
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.material)
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.tooling.preview)
        implementation(libs.material)
        implementation(libs.material3)
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.navigation.compose)
        debugImplementation(libs.androidx.compose.ui.tooling)

        ksp(libs.androidx.room.compiler)

        implementation(libs.androidx.datastore.preferences)
        implementation(libs.androidx.datastore.core)

        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)

        implementation(libs.kotlinx.serialization.json)
        implementation(libs.kotlinx.datetime)
        implementation(libs.coroutines)
        implementation(libs.retrofit)
        implementation(libs.okhttp)
        implementation("com.squareup.retrofit2:converter-moshi:3.0.0")
        implementation("com.squareup.retrofit2:converter-kotlinx-serialization:1.0.0")
        implementation("com.squareup.okhttp3:logging-interceptor:${libs.versions.okhttp.get()}")
        implementation(libs.androidx.work.runtime.ktx)
        implementation(libs.androidx.security.crypto)
        implementation(libs.androidx.hilt.navigation)

        implementation(platform(libs.firebase.bom))
        implementation(libs.firebase.auth)

        implementation(libs.libsu.core)
        implementation(libs.libsu.io)

        implementation(libs.timber)

        compileOnly(libs.xposed.api)
        compileOnly(libs.yukihookapi)

        coreLibraryDesugaring(libs.desugar.jdk.libs)

        testImplementation(libs.junit.jupiter.api)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.hilt.android.testing)
        androidTestImplementation(libs.androidx.benchmark.junit4)
        androidTestImplementation(libs.androidx.test.uiautomator)

        // Logging API only (do not bind implementation at runtime for libraries)
        implementation(libs.slf4j.api)

        // Testing (JUnit 5)
        testImplementation(libs.junit.jupiter.api)
        testRuntimeOnly(libs.junit.jupiter.engine)
        testImplementation(libs.mockk)
    }
}
