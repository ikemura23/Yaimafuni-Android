pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

include(
    ":androidApp",
    ":domain",
    ":data",
    ":app",
)
