plugins {
    id("accoutkt.multiplatform")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.accountkt.ui.design.components"
}

dependencies {
    commonMainApi(projects.ui.design.core)
}