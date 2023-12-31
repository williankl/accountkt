plugins {
    id("accoutkt.multiplatform")
}

android {
    namespace = "williankl.accountkt.feature.currencyFeature"
}

dependencies {
    commonMainImplementation(projects.data.currencyService)
    commonMainImplementation(projects.data.currencyStorage)
    commonMainImplementation(libs.kotlinx.datetime)
    commonMainImplementation(libs.kodein.core)
}

