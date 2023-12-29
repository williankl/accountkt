plugins {
    id("accoutkt.multiplatform")
    id("app.cash.sqldelight")
}

android {
    namespace = "williankl.accountkt.data.currencyStorage"
}

dependencies {
    commonMainImplementation(projects.data.currencyService)
    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(libs.cashApp.sqLiteRuntime)
    androidMainImplementation(libs.cashApp.sqLiteAndroid)
    iosMainImplementation(libs.cashApp.sqLiteNativeDriver)
}

sqldelight {
    databases {
        create("CurrencyDatabase") {
            packageName.set("williankl.accountkt.data.currencyStorage")
        }
    }
}

