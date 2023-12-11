package williankl.accountkt.data.currencyStorage.driver

import app.cash.sqldelight.db.SqlDriver

internal expect class DriverProvider {
    fun provide(): SqlDriver
}