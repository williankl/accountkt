plugins {
    id("accoutkt.android.app")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.accountkt.app.android"
}

dependencies {
    implementation(projects.feature.currencyFeature)
    implementation(projects.feature.sharedPreferences)
    implementation(projects.data.currencyStorage)
    implementation(projects.data.currencyService)

    implementation(libs.android.compose.activity)
    implementation(libs.kodein.android)
    implementation(libs.kodein.compose)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.transitions)
    implementation(libs.voyager.kodein)
    implementation(libs.kamelMedia)

    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.ui)
    implementation(compose.animation)
    implementation(compose.material)
}