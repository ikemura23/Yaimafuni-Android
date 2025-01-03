plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.yaeyama.linerchecker"
    compileSdk = libs.versions.app.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.app.minSdk.get().toInt()
    }
}

dependencies {
    implementation(libs.koin.core)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)
    // coroutines
    implementation(libs.coroutines.android)
    implementation(project(":domain"))
}
