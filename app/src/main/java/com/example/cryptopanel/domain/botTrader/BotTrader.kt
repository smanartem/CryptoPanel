package com.example.cryptopanel.domain.botTrader

import android.content.SharedPreferences
import com.example.cryptopanel.data.data.localDb.LogDao
import com.example.cryptopanel.data.data.localDb.LogTransaction
import com.example.cryptopanel.data.data.model.TradeCoin
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val BALANCE_PREFS = "balance prefs"
class BotTrader(
    private val dao: LogDao,
    private val prefs: SharedPreferences
) {
    //TODO:must be inject by Koin prefs here
  //  private val prefs: SharedPreferences by inject(named("balancePrefs"))

    private val currentPercent = 0.01
    private var balanceUsd = 0.0
    private var balanceCoin = 0.0
    fun startOperation(coin: TradeCoin) {
        loadBalance()
        if (coin.priceChange24h > currentPercent) {
            buy(coin)
        } else {
            sell(coin)
        }
    }

    private fun buy(coin: TradeCoin) {
        val operation = "WAS BUY"

        if (balanceUsd > 0) {
            val coins = convertUsdToCoin(balanceUsd, coin)
            val information = "$operation $coins FOR $balanceUsd$"
            insertTransaction(operation, getCurrentTime(), information)
            changeAndSaveBalance(0.0, coins)
        }
    }

    private fun sell(coin: TradeCoin) {
        val operation = "WAS SELL"
        if (balanceCoin > 0) {
            val usd = convertCoinToUsd(balanceCoin, coin)
            val information = "$operation $balanceCoin FOR $usd"
            insertTransaction(operation, getCurrentTime(), information)
            changeAndSaveBalance(usd, 0.0)
        }
    }

    private fun convertCoinToUsd(balanceCoins: Double, coin: TradeCoin): Double {
        return balanceCoins * coin.price
    }

    private fun convertUsdToCoin(balanceUsd: Double, coin: TradeCoin): Double {
        return balanceUsd / coin.price
    }

    private fun insertTransaction(operation: String, time: String, information: String) {
        val transaction = LogTransaction(time, operation, information)
        dao.insertLogTransactions(transaction)
    }

    private fun changeAndSaveBalance(usd: Double, coins: Double) {
        balanceUsd = usd.also {
            prefs.edit().putFloat("balanceUsd", usd.toFloat()).apply()
        }
        balanceCoin = coins.also {
            prefs.edit().putFloat("balanceCoins", coins.toFloat()).apply()
        }
    }

    private fun getCurrentTime(): String {
        return LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a"))
    }

    private fun loadBalance(){
        val balanceUsdPrefs = prefs.getFloat("balanceUsd", 0.0F)
        val balanceCoinsPrefs = prefs.getFloat("balanceCoins",0.0F)

        balanceUsd = balanceUsdPrefs.toDouble()
        balanceCoin = balanceCoinsPrefs.toDouble()
    }
}