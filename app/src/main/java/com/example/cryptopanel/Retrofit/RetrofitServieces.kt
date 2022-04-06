package com.example.cryptopanel.Retrofit

import com.example.cryptopanel.Model.Coin
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitServieces {
    @GET("coins/markets?vs_currency=usd")
    suspend fun getCoins(): Response<List<Coin>>
}