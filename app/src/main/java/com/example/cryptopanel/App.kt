package com.example.cryptopanel

import android.app.Application
import com.example.cryptopanel.retrofit.RetrofitClient

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.init()
    }
}

//структура проекта по типу папки со всеми вьюмоделями, со веми фрагментами и ТД не очень удобна
//лучше хранить всё что относится к одному экрану в одной папке, например фрагмент, вьюмодель и адаптер для ресйклера