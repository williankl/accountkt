enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io/")
        mavenCentral()
        google()
    }
}

rootProject.name = "accountkt"
include(":app:android")

include(":ui:application")
include(":ui:design:core")
include(":ui:design:components")

include(":feature:currency-feature")
include(":feature:shared-preferences")

include(":data:currency-service")
include(":data:currency-storage")
