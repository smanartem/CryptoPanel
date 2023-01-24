package com.example.cryptopanel.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideCoinGeckoApi(retrofit: Retrofit): CoinGeckoApi {
    return retrofit.create(CoinGeckoApi::class.java)
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://example.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}