plugins {
    id("accoutkt.multiplatform")
}

android {
    namespace = "williankl.accountkt.feature.sharedPreferences"
}

dependencies {
    commonMainImplementation(projects.data.currencyService)
    commonMainImplementation(projects.ui.design.core)

    commonMainApi(libs.kmpSettings)
    commonMainImplementation(libs.kodein.core)
}

