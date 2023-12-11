package williankl.accountkt.data.currencyStorage.driver

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import williankl.accountkt.data.currencyStorage.CurrencyDatabase

internal actual class DriverProvider {
    actual fun provide(): SqlDriver {
        return  NativeSqliteDriver(CurrencyDatabase.Schema, "currency.db")
    }
}