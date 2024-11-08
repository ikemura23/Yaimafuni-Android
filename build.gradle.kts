import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        classpath(libs.google.services)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.navigation.safe.args.gradle.plugin)
        classpath(libs.firebase.crashlytics.gradle)
    }
}

allprojects {
    repositories {
        google()
        maven("https://jitpack.io")
        maven("https://maven.google.com")
        mavenCentral()
    }
}

subprojects {
    plugins.withId("org.jetbrains.kotlin.android") {
        extensions.configure<KotlinAndroidProjectExtension> {
            jvmToolchain(17)
        }
    }
    plugins.withId("org.jetbrains.kotlin.jvm") {
        extensions.configure<KotlinJvmProjectExtension> {
            jvmToolchain(17)
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
