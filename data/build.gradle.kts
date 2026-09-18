plugins {
    id("yaeyama.android.library")
}

android {
    namespace = "com.yaeyama.linerchecker.data"

    buildTypes {
        create("mock") {
            initWith(getByName("debug"))
            matchingFallbacks += listOf("debug")
        }
    }
}

dependencies {
    implementation(libs.koin.core)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)
    // coroutines
    implementation(libs.coroutines.android)
    implementation(libs.timber)
    implementation(project(":domain"))

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
}
