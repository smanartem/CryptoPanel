package com.example.cryptopanel

import android.app.Application
import com.example.cryptopanel.retrofit.RetrofitClient

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.init()
    }
}