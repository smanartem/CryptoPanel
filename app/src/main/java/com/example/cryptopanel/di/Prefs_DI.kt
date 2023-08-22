package com.example.cryptopanel.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.cryptopanel.MODE
import com.example.cryptopanel.domain.botTrader.BALANCE_PREFS
import com.example.cryptopanel.ui.mainScreen.TOPLIST
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val prefsModule = module {
    single<SharedPreferences>(named("modePrefs")) { getModePrefs(androidApplication(), MODE) }
    single<SharedPreferences>(named("topListPrefs")) {
        getTopListPrefs(androidApplication(), TOPLIST)
    }
    single<SharedPreferences>(named("balancePrefs")) {
        getBalancePrefs(androidApplication(), BALANCE_PREFS)
    }
}

private fun getModePrefs(app: Application, name: String): SharedPreferences {
    return app.getSharedPreferences(name, MODE_PRIVATE)
}

private fun getTopListPrefs(app: Application, name: String): SharedPreferences {
    return app.getSharedPreferences(name, MODE_PRIVATE)
}

private fun getBalancePrefs(app: Application, name: String): SharedPreferences {
    return app.getSharedPreferences(name, MODE_PRIVATE)
}