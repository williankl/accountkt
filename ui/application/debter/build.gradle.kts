import com.codingfeline.buildkonfig.compiler.FieldSpec
import williankl.accountkt.buildSrc.helpers.applyCommonMainCodeGeneration
import williankl.accountkt.buildSrc.helpers.applyMultiModuleKsp
import williankl.accountkt.buildSrc.helpers.applyNativeWithBaseName
import williankl.accountkt.buildSrc.helpers.commonMainLyricistImplementation
import williankl.accountkt.buildSrc.helpers.retrieveVersionFromCatalogs

plugins {
    id("accoutkt.multiplatform")
    id("org.jetbrains.compose")
    id("dev.icerock.mobile.multiplatform-resources")
    id("com.codingfeline.buildkonfig")
    id("com.google.devtools.ksp")
}

applyNativeWithBaseName("DebterApplication")
applyCommonMainCodeGeneration()
applyMultiModuleKsp("williankl.debter.ui.application")

android {
    namespace = "williankl.debter.ui.application"
}

dependencies {
    commonMainImplementation(projects.feature.currencyFeature)
    commonMainImplementation(projects.feature.sharedPreferences)
    commonMainImplementation(projects.data.currencyStorage)
    commonMainImplementation(projects.data.currencyService)
    commonMainImplementation(projects.ui.design.components)

    commonMainImplementation(libs.lyricist.core)
    commonMainLyricistImplementation(libs.lyricist.ksp)

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
            type = FieldSpec.Type.STRING,
            name = "appVersion",
            value = retrieveVersionFromCatalogs("versionName-accountkt")
        )
    }
}