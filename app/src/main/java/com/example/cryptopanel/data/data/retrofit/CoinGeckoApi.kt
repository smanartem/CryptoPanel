package com.example.cryptopanel.data.data.retrofit

import com.example.cryptopanel.data.data.model.CoinDataModel
import com.example.cryptopanel.data.data.model.TradeCoin
import com.example.cryptopanel.data.data.model.Trend
import retrofit2.Response
import retrofit2.http.GET

interface CoinGeckoApi {
    @GET("http://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&sparkline=true")
    suspend fun getCoins(): Response<List<CoinDataModel>>

    @GET("http://api.coingecko.com/api/v3/search/trending")
    suspend fun getTrendCoins(): Response<Trend>

    @GET("http://api.coingecko.com/api/v3/simple/price?ids=terra-luna&vs_currencies=usd&include_24hr_change=true")
    suspend fun getOneCoin(): Response<TradeCoin>
}