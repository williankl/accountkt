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
    commonMainApi(compose.ui)
    commonMainApi(compose.animation)
    commonMainApi(compose.material)
    commonMainApi(compose.material3)

    commonMainApi(libs.kamelMedia)
}