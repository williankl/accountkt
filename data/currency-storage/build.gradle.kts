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
    androidMainImplementation(libs.cashapp.sqLiteAndroid)
    iosMainImplementation(libs.cashapp.sqLiteNativeDriver)
}

sqldelight {
    databases {
        create("CurrencyDatabase") {
            packageName.set("williankl.accountkt.data.currencyStorage")
        }
    }
}

