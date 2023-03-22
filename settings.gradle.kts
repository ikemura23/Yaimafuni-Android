pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

include(
    ":shared",
    ":androidApp",
    ":domain",
    ":data",
)
