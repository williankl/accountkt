plugins {
    id("accoutkt.multiplatform")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.accountkt.ui.design.core"
}

dependencies {
    commonMainApi(compose.runtime)
    commonMainApi(compose.foundation)
    commonMainImplementation(compose.ui)
    commonMainImplementation(compose.animation)
    commonMainImplementation(compose.material)
    commonMainImplementation(compose.material3)

    commonMainApi(libs.kamelMedia)
}