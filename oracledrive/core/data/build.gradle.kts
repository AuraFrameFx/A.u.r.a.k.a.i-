plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}


android {
    namespace = "dev.aurakai.auraframefx.core.data"
    compileSdk = libs.versions.compile.sdk.get().toInt()
}

dependencies {
    // Expose domain layer
    api(project(":core:domain"))
    compileOnly(libs.xposed.api)
    compileOnly(libs.yukihookapi)
    implementation(libs.androidx.core.ktx)

    // Data layer dependencies
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // Hilt - required when alias(libs.plugins.hilt) is applied
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Common utilities
    implementation(project(":core:common"))

    // Testing

    implementation("com.github.topjohnwu.libsu:core:6.0.0")
    implementation("com.github.topjohnwu.libsu:io:6.0.0")
    implementation("com.github.topjohnwu.libsu:service:6.0.0")
    compileOnly("de.robv.android.xposed:api:82")
    implementation("com.squareup.retrofit2:converter-kotlinx-serialization:1.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:${libs.versions.okhttp.get()}")
}
