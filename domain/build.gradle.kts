plugins {
    id("yaeyama.kotlin.library")
}

dependencies {
    implementation(libs.coroutines.core)

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
}
