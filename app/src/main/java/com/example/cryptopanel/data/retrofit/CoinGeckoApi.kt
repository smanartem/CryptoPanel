package com.example.cryptopanel.data.retrofit

import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.model.Trend
import retrofit2.Response
import retrofit2.http.GET

interface CoinGeckoApi {
    @GET("http://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&sparkline=true")
    suspend fun getCoins(): Response<List<Coin>>

    @GET("http://api.coingecko.com/api/v3/search/trending")
    suspend fun getTrendCoins(): Response<Trend>
}