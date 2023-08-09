import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension

plugins {
    id("com.android.application")
    id("com.google.gms.google-services") // firebaseに必要
    kotlin("android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.firebase.crashlytics")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = libs.versions.app.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.banbara.yaeyama.liner.checker"
        minSdk = libs.versions.app.minSdk.get().toInt()
        targetSdk = libs.versions.app.targetSdk.get().toInt()
        versionCode = 88
        versionName = "4.6.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            applicationIdSuffix = ".debug"
            configure<CrashlyticsExtension> {
                // マッピング ファイルを Crashlytics にアップロードしない
                // https://firebase.google.com/docs/crashlytics/upgrade-sdk?hl=ja&platform=android
                mappingFileUploadEnabled = false
            }
        }
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    namespace = "com.ikmr.banbara23.yaeyama_liner_checker"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    // android
    implementation(libs.androidx.core)
    implementation(libs.appcompat)
    implementation(libs.browser)
    implementation(libs.constraintlayout)
    implementation(libs.viewpager2)
    // navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.navigation.runtime.ktx)
    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    // coroutines
    implementation(libs.coroutines.android)

    // Coil
    implementation(libs.coil.compose)

    implementation(libs.timber)

    // In-App Review
    implementation(libs.play.core)
    implementation(libs.play.core.ktx)

    // Jetpack Compose toolkit dependencies
    // https://developer.android.com/jetpack/compose/setup#compose-compiler
    implementation(libs.compose.ui)
    // Tooling support (Previews, etc.)
    implementation(libs.compose.ui.tooling)
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation(libs.compose.foundation)
    implementation(libs.lifecycle.viewmodel.compose)
    // Material Design
    implementation(libs.compose.material)
    // Material design icons
    implementation(libs.material.icons.core)
    implementation(libs.material.icons.extended)
    implementation(libs.compose.theme.adapter)
    implementation(libs.ui.tooling.preview)
    implementation(libs.activity.compose)
    // UI Tests
    androidTestImplementation(libs.ui.test.junit4)

    // Koin
    implementation(libs.koin.android)

    testImplementation(libs.junit)

    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.0") {
        exclude(group = "com.android.support", module = "support-annotations")
    }
}
