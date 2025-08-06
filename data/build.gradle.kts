plugins {
    id("yaeyama.android.library")
}

android {
    namespace = "com.yaeyama.linerchecker"
}

dependencies {
    implementation(libs.koin.core)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)
    // coroutines
    implementation(libs.coroutines.android)
    implementation(project(":domain"))
}
