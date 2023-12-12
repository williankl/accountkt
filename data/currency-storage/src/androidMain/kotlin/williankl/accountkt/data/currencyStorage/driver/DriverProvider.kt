package williankl.accountkt.data.currencyStorage.driver

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import williankl.accountkt.data.currencyStorage.CurrencyDatabase

internal actual class DriverProvider(
    private val context: Context
) {
    actual fun provide(): SqlDriver {
        return AndroidSqliteDriver(CurrencyDatabase.Schema, context, "currency.db")
    }
}