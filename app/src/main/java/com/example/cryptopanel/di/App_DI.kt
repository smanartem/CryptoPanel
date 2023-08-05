package com.example.cryptopanel.di

import com.example.cryptopanel.ui.mainScreen.CryptoPanelViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { CryptoPanelViewModel(get(), get()) }
}

val appModule = viewModelModule + networkModule + prefsModule