package com.example.cryptopanel.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    lateinit var coinGeckoApi: CoinGeckoApi

    fun init() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        coinGeckoApi = retrofit.create(CoinGeckoApi::class.java)

    }
}