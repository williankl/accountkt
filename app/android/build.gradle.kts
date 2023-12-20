plugins {
    id("accoutkt.android.app")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.accountkt.app.android"
}

dependencies {
    implementation(projects.ui.application)
    implementation(libs.android.compose.activity)
    implementation(libs.kodein.android)
    implementation(libs.kodein.compose)
}