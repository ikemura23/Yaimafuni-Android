import com.android.build.api.dsl.ApplicationExtension
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.firebase.crashlytics")
}

val libs = the<VersionCatalogsExtension>().named("libs")

configure<ApplicationExtension> {
    compileSdk = libs.findVersion("app-compileSdk").get().requiredVersion.toInt()

    defaultConfig {
        minSdk = libs.findVersion("app-minSdk").get().requiredVersion.toInt()
        targetSdk = libs.findVersion("app-targetSdk").get().requiredVersion.toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = false
            }
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlinExtension.jvmToolchain(17)
