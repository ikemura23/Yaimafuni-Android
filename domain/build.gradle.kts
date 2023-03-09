plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}")
//    implementation("dev.gitlive:firebase-database:${Versions.firebaseDatabase}")
//    implementation("io.insert-koin:koin-core:${Versions.koin}")
}
