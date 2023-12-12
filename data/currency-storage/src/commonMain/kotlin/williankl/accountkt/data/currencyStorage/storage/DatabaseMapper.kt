package williankl.accountkt.data.currencyStorage.storage

import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolRate
import williankl.accountkt.data.currencyStorage.DatabaseSymbolRate

internal object DatabaseMapper {
    fun mapToDomain(storageModel: DatabaseSymbolRate): SymbolRate {
        return with(storageModel) {
            SymbolRate(
                base = base,
                rates = stringToRates(rates),
                nextUpdateAt = nextUpdateAt,
            )
        }
    }

    fun mapToDatabaseModel(rate: SymbolRate): DatabaseSymbolRate {
        return with(rate) {
            DatabaseSymbolRate(
                base = base,
                rates = ratesToString(rates),
                nextUpdateAt = nextUpdateAt,
            )
        }
    }

    private fun stringToRates(str: String): Map<Symbol, Double> {
        return str.split("|").associate { rawString ->
            val label = rawString.substringBefore("-")
            val value = rawString.substringAfter("-").toDouble()
            label to value
        }
    }

    private fun ratesToString(rates: Map<Symbol, Double>): String {
        return rates.map { (symbol, value) ->
            symbol to value
        }.joinToString(separator = "|") { (symbol, value) ->
            "$symbol-$value"
        }
    }
}