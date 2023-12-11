import com.codingfeline.buildkonfig.compiler.FieldSpec
import williankl.accountkt.buildSrc.helpers.fromLocalProperties

plugins {
    id("accoutkt.multiplatform")
    id("com.codingfeline.buildkonfig")
}

android {
    namespace = "williankl.accountkt.data.currencyService"
}

dependencies {
    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(libs.ktor.client.core)
    commonMainImplementation(libs.ktor.client.contentNegotiation)
    commonMainImplementation(libs.ktor.client.jsonSerialization)
    commonMainImplementation(libs.ktor.client.logging)

    androidMainImplementation(libs.ktor.client.okHttp)
    iosMainImplementation(libs.ktor.client.darwin)
}

buildkonfig {
    packageName = "williankl.accountkt.data.currencyService"
    defaultConfigs {
        buildConfigField(
            FieldSpec.Type.STRING, "apiKey", fromLocalProperties("apiKey")
        )
    }
}