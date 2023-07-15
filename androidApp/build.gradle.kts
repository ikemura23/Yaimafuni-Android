import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude

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
    compileSdk = DefaultConfig.compileSdk

    defaultConfig {
        applicationId = "com.banbara.yaeyama.liner.checker"
        minSdk = DefaultConfig.minSdk
        targetSdk = DefaultConfig.targetSdk
        versionCode = 79
        versionName = "4.4.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false // TODO: trueにしたほうがいい
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
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
    implementation("androidx.compose.ui:ui:${Versions.compose}")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:${Versions.compose}")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:${Versions.compose}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-rc01")
    // Material Design
    implementation("androidx.compose.material:material:${Versions.compose}")
    // Material design icons
    implementation("androidx.compose.material:material-icons-core:${Versions.compose}")
    implementation("androidx.compose.material:material-icons-extended:${Versions.compose}")
    implementation("com.google.android.material:compose-theme-adapter:${Versions.composeThemeAdapter}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Versions.compose}")
    implementation("androidx.activity:activity-compose:${Versions.activityCompose}")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Versions.compose}")

    // Koin
    implementation(libs.koin.android)

    testImplementation("junit:junit:${Versions.junit}")

    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.0") {
        exclude(group = "com.android.support", module = "support-annotations")
    }
}
