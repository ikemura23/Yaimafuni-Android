plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "com.banbara.yaeyama.liner.checker"
        minSdk = 24
        targetSdk = 30
        versionCode = 73
        versionName = "4.3.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles("proguard-android.txt") // TODO: 自信がない

            // proguardFiles getDefaultProguardFile ("proguard-android.txt"), "proguard-rules.pro"
        }
        debug {
            applicationIdSuffix = ".debug"
            // ext.enableCrashlytics = false // TODO: あとで解決する
        }
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.1"
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
}

dependencies {
    implementation(project(":shared"))

    // android
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.vectordrawable:vectordrawable:1.1.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.browser:browser:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha03")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.fragment:fragment-ktx:1.3.6")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    val nav_version = "2.3.5" // TODO: 仮ローカル変数、rootの変数を参照していたがKotlin-dsl変換中はとりあえずここを参照させる
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.navigation:navigation-runtime-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    // google
    implementation("com.google.android.gms:play-services-base:17.6.0")
    // firebase
    implementation(platform("com.google.firebase:firebase-bom:26.4.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-crashlytics")
    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")

    // Picasso
    implementation("com.squareup.picasso:picasso:2.71828")

    implementation("com.jakewharton.timber:timber:4.7.1")
    val kotlin_version = "1.5.21" // TODO: 仮ローカル変数、rootの変数を参照していたがKotlin-dsl変換中はとりあえずここを参照させる
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // In-App Review
    implementation("com.google.android.play:core:1.10.0")
    implementation("com.google.android.play:core-ktx:1.8.1")

    val compose_version = "1.0.1" // TODO: 仮ローカル変数、rootの変数を参照していたがKotlin-dsl変換中はとりあえずここを参照させる
    // Jetpack Compose toolkit dependencies
    // https://developer.android.com/jetpack/compose/setup#compose-compiler
    implementation("androidx.compose.ui:ui:$compose_version")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:$compose_version")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:$compose_version")
    // Material Design
    implementation("androidx.compose.material:material:$compose_version")
    // Material design icons
    implementation("androidx.compose.material:material-icons-core:$compose_version")
    implementation("androidx.compose.material:material-icons-extended:$compose_version")
    implementation("com.google.android.material:compose-theme-adapter:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation("androidx.activity:activity-compose:1.3.1")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")

    // Koin
    implementation("io.insert-koin:koin-android:3.1.2")

    testImplementation("junit:junit:4.13.2")

    // TODO: 一時的にコメントアウト、書き方がよく分かってない
    // androidTestImplementation("androidx.test.espresso:espresso-core:3.1.0"), {
    // exclude group : "com.android.support", module: "support-annotations" })
}
