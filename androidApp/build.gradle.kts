plugins {
    id("yaeyama.android.application")
}

android {
    namespace = "com.ikmr.banbara23.yaeyama_liner_checker"

    defaultConfig {
        applicationId = "com.banbara.yaeyama.liner.checker"
        versionCode = 94
        versionName = "4.8.0"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
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
    implementation(libs.play.review)
    implementation(libs.play.review.ktx)

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
