import com.codingfeline.buildkonfig.compiler.FieldSpec
import williankl.accountkt.buildSrc.helpers.applyNativeWithBaseName
import williankl.accountkt.buildSrc.helpers.retrieveVersionFromCatalogs

plugins {
    id("accoutkt.multiplatform")
    id("org.jetbrains.compose")
    id("dev.icerock.mobile.multiplatform-resources")
    id("com.codingfeline.buildkonfig")
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
    commonMainImplementation(libs.voyager.screenModel)
    commonMainImplementation(libs.voyager.bottomSheetNavigator)
    commonMainImplementation(libs.voyager.transitions)
    commonMainImplementation(libs.voyager.kodein)
    commonMainImplementation(libs.moko.resourcesCompose)
    commonMainImplementation(libs.composeIcons.eva)
}

buildkonfig {
    packageName = "williankl.accountkt.ui.application"
    defaultConfigs {
        buildConfigField(
            FieldSpec.Type.STRING, "appVersion", retrieveVersionFromCatalogs("versionName")
        )
    }
}