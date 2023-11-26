package com.example.cryptopanel.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.cryptopanel.utils.MODE
import com.example.cryptopanel.utils.TOPLIST
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val prefsModule = module {
    single<SharedPreferences>(named("modePrefs")) { getModePrefs(androidApplication(), MODE) }
    single<SharedPreferences>(named("topListPrefs")) {
        getTopListPrefs(androidApplication(), TOPLIST)
    }
}

//TODO: try to replace 4 funs on 1 common

private fun getModePrefs(app: Application, name: String): SharedPreferences {
    return app.getSharedPreferences(name, MODE_PRIVATE)
}

private fun getTopListPrefs(app: Application, name: String): SharedPreferences {
    return app.getSharedPreferences(name, MODE_PRIVATE)
}