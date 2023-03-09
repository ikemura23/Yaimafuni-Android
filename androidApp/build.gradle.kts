import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
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
    kotlinOptions {
        jvmTarget = "11"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    // TODO: 一時的に外す、kts化が必要
    // Navigation Componentの Directionsが認識されない対応
    // https://stackoverflow.com/a/67875849
    // sourceSets {
    //     main {
    //         java {
    //             srcDir("build/generated/source/navigation-args")
    //         }
    //     }
    // }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.ikmr.banbara23.yaeyama_liner_checker"
}

dependencies {
    implementation(project(":shared"))

    // android
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.browser:browser:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-beta01")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("androidx.fragment:fragment-ktx:1.4.1")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    // navigation
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.navigation}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.navigation}")
    implementation("androidx.navigation:navigation-runtime-ktx:${Versions.navigation}")
    // google
    // implementation("com.google.android.gms:play-services-base:18.0.1")
    // firebase
    implementation(platform("com.google.firebase:firebase-bom:30.0.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-crashlytics")
    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}")

    // Picasso TODO: 後に削除
//    implementation("com.squareup.picasso:picasso:2.71828")
    // Coil
    implementation("io.coil-kt:coil-compose:2.1.0")

    implementation("com.jakewharton.timber:timber:4.7.1")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // In-App Review
    implementation("com.google.android.play:core:1.10.3")
    implementation("com.google.android.play:core-ktx:1.8.1")

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
    implementation("io.insert-koin:koin-android:${Versions.koin}")

    implementation(project(":domain"))

    testImplementation("junit:junit:${Versions.junit}")

    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.0") {
        exclude(group = "com.android.support", module = "support-annotations")
    }
}
