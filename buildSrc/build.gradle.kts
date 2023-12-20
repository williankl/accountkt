plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

gradlePlugin {
    plugins.register("multiplatform-module") {
        id = "accoutkt.multiplatform"
        implementationClass = "williankl.accountkt.buildSrc.MultiplatformModulePlugin"
    }
    plugins.register("android-app-module") {
        id = "accoutkt.android.app"
        implementationClass = "williankl.accountkt.buildSrc.AndroidAppModulePlugin"
    }
    plugins.register("kotlin-module") {
        id = "accoutkt.kotlin"
        implementationClass = "williankl.accountkt.buildSrc.KotlinModulePlugin"
    }
}

dependencies {
    implementation(libs.plugin.android)
    implementation(libs.plugin.cashApp.sqlDelight)
    implementation(libs.plugin.google.crashlytics)
    implementation(libs.plugin.google.services)
    implementation(libs.plugin.kotlin)
    implementation(libs.plugin.kotlin.serialization)
    implementation(libs.plugin.multiplatform.compose)
    implementation(libs.plugin.ksp)
    implementation(libs.plugin.buildKonfig)
    implementation(libs.plugin.ktlint)
    implementation(libs.plugin.detekt)
}