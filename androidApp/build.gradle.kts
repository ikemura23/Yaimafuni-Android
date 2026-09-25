plugins {
    id("yaeyama.android.application")
}

android {
    namespace = "com.yaeyama.linerchecker"

    defaultConfig {
        applicationId = "com.banbara.yaeyama.liner.checker"
        versionCode = 97
        versionName = "4.9.1"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        create("mock") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".mock"
            matchingFallbacks += listOf("debug")
        }
    }

    androidResources {
        // values-xx/ を追加するだけで Android 13 以降のアプリ別言語設定に対応言語が表示されるよう、
        // res/ の言語から LocaleConfig を自動生成する（既定言語は res/resources.properties で日本語に指定）
        generateLocaleConfig = true
    }

    testOptions {
        unitTests {
            // Robolectric で Compose UI テストを動かすためにリソースを含める
            isIncludeAndroidResources = true
        }
    }
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
    implementation(libs.play.review)
    implementation(libs.play.review.ktx)

    // Jetpack Compose toolkit dependencies
    // https://developer.android.com/jetpack/compose/setup#compose-compiler
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    // Tooling support (Previews, etc.)
    implementation(libs.compose.ui.tooling)
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation(libs.compose.foundation)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)
    // Material Design
    implementation(libs.compose.material)
    implementation(libs.compose.material3)
    // Material design icons
    implementation(libs.material.icons.core)
    implementation(libs.material.icons.extended)
    implementation(libs.compose.theme.adapter)
    implementation(libs.ui.tooling.preview)
    implementation(libs.activity.compose)

    // Koin
    implementation(libs.koin.android)

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)
    // Compose UI テスト（Robolectric 上で JVM テストとして実行する）
    testImplementation(platform(libs.compose.bom))
    testImplementation(libs.ui.test.junit4)
    testImplementation(libs.robolectric)
}
