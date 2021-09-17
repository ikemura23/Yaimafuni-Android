// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    // ext {
    val compose_version = "1.0.1"
    val kotlin_version = "1.5.21"
    val gradlePluginVersion = "7.0.2"
    val nav_version = "2.3.5"
    // }

    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${gradlePluginVersion}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        classpath("com.google.gms:google-services:4.3.10")

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.7.1")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
        maven("https://maven.google.com")
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
