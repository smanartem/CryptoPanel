package com.example.cryptopanel.retrofit

import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

private const val API_NEWS_KEY = "b99dbbe56f204d26a827539f431417a2"

interface CoinGeckoApi {
    @GET("http://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&sparkline=true")
    suspend fun getCoins(): Response<List<Coin>>

    @GET("http://newsapi.org/v2/everything?q=Etherium&sortBy=popularity&apiKey=$API_NEWS_KEY")
    suspend fun getNews(): Response<NewsResponse>
}