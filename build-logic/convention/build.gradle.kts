plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.gradle)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.google.services)
    implementation(libs.firebase.crashlytics.gradle)
}

kotlin {
    jvmToolchain(17)
}