plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.yaeyama.linerchecker"
    compileSdk = 33

    // Unitテストで使う
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildTypes {
        getByName("debug")
        getByName("release")
    }
}

dependencies {
    implementation("io.insert-koin:koin-core:${Versions.koin}")

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)
    // coroutines
    implementation(libs.coroutines.android)
    implementation(project(":domain"))
}
