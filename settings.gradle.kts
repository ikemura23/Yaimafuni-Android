pluginManagement {
    includeBuild("build-logic")
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
)
