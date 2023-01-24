package com.example.cryptopanel

import com.example.cryptopanel.retrofit.CoinGeckoApi
import com.example.cryptopanel.retrofit.provideCoinGeckoApi
import com.example.cryptopanel.retrofit.provideRetrofit
import com.example.cryptopanel.viewModels.CryptoPanelViewModel
import com.example.cryptopanel.viewModels.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single<CoinGeckoApi> { provideCoinGeckoApi(get()) }
    single<Retrofit> { provideRetrofit() }

    viewModel { CryptoPanelViewModel(get(), get()) }
    viewModel { NewsViewModel(get()) }
}