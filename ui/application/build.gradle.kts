import williankl.accountkt.buildSrc.helpers.applyNativeWithBaseName

plugins {
    id("accoutkt.multiplatform")
    id("org.jetbrains.compose")
}

applyNativeWithBaseName("KtApplication")

android {
    namespace = "williankl.accountkt.ui.application"
}

dependencies {
    commonMainImplementation(projects.feature.currencyFeature)
    commonMainImplementation(projects.feature.sharedPreferences)
    commonMainImplementation(projects.data.currencyStorage)
    commonMainImplementation(projects.data.currencyService)
    commonMainImplementation(projects.ui.design.components)

    commonMainImplementation(libs.kodein.compose)
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.kotlinx.serialization.json)
    commonMainImplementation(libs.voyager.navigator)
    commonMainImplementation(libs.voyager.bottomSheetNavigator)
    commonMainImplementation(libs.voyager.transitions)
    commonMainImplementation(libs.voyager.kodein)
    commonMainImplementation(libs.moko.resourcesCompose)
}