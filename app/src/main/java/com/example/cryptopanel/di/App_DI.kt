package com.example.cryptopanel.di

import com.example.cryptopanel.viewModels.CryptoPanelViewModel
import com.example.cryptopanel.viewModels.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { CryptoPanelViewModel(get(), get()) }
    viewModel { NewsViewModel(get()) }
}

val appModule = viewModelModule + networkModule + prefsModule