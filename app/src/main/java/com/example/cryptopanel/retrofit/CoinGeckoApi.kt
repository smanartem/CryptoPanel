package com.example.cryptopanel.retrofit

import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_NEWS_KEY = "b99dbbe56f204d26a827539f431417a2"

interface CoinGeckoApi {
    @GET("http://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&sparkline=true")
    suspend fun getCoins(): Response<List<Coin>>

    @GET("http://newsapi.org/v2/everything?sortBy=popularity")
    suspend fun getNews(
        @Query("q")
        searchQuery: String = "Crypto",
        @Query("apiKey")
        apiKey: String = API_NEWS_KEY
    ): Response<NewsResponse>
}