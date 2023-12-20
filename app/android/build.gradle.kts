plugins {
    id("accoutkt.android.app")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
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

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
}