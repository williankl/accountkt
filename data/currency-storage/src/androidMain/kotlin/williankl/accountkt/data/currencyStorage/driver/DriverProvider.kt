package williankl.accountkt.data.currencyStorage.driver

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
internal actual class DriverProvider(
    private val context: Context
) {
    actual fun provide(): SqlDriver {
        return  TODO()//AndroidSqliteDriver(CurrencyDatabase.Schema, context, "currency.db")
    }
}