package com.example.cryptopanel.Retrofit

import com.example.cryptopanel.Model.Coin
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitServieces {
    @GET("coins/markets?vs_currency=usd")
    fun getCoins(): Call<List<Coin>>
}