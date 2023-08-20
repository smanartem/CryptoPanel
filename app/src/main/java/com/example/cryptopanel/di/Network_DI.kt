package com.example.cryptopanel.di

import com.example.cryptopanel.data.data.retrofit.CoinGeckoApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module{
    single<CoinGeckoApi> { provideCoinGeckoApi(get()) }
    single<Retrofit> { provideRetrofit() }
}

private fun provideCoinGeckoApi(retrofit: Retrofit): CoinGeckoApi {
    return retrofit.create(CoinGeckoApi::class.java)
}

private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://example.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}