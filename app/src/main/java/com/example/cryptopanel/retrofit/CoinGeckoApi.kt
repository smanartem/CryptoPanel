package com.example.cryptopanel.retrofit

import com.example.cryptopanel.model.Coin
import retrofit2.Response
import retrofit2.http.GET

interface CoinGeckoApi {
    @GET("coins/markets?vs_currency=usd&sparkline=true")
    suspend fun getCoins(): Response<List<Coin>>
}