package com.example.cryptopanel.retrofit

import com.example.cryptopanel.BuildConfig
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.model.NewsResponse
import com.example.cryptopanel.model.Trend
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val KEY = BuildConfig.API_KEY

interface CoinGeckoApi {
    @GET("http://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&sparkline=true")
    suspend fun getCoins(): Response<List<Coin>>

    @GET("http://api.coingecko.com/api/v3/search/trending")
    suspend fun getTrendCoins(): Response<Trend>

    @GET("http://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&sparkline=true")
    suspend fun getTrendTopCoins(
        @Query("ids")
        ids: String
    ): Response<List<Coin>>

    @GET("http://newsapi.org/v2/everything?sortBy=popularity")
    suspend fun getNews(
        @Query("q")
        searchQuery: String = "Crypto",
        @Query("apiKey")
        apiKey: String = KEY
    ): Response<NewsResponse>
}