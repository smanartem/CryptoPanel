package com.example.cryptopanel.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.model.NewsResponse
import com.example.cryptopanel.retrofit.RetrofitClient
import kotlinx.coroutines.*

class CryptoPanelViewModel(application: Application) : AndroidViewModel(application) {
    val coinsList = MutableLiveData<List<Coin>>()
    val news = MutableLiveData<NewsResponse>()

    fun getAllCoins() {
        if (coinsList.value.isNullOrEmpty()) {
            MainScope().launch {
                coinsList.postValue(getDataCoins()!!)
            }
        }
    }

    fun getAllNews() = viewModelScope.launch {
        news.postValue(getDataNews()!!)
    }

    fun setSortArray(item: List<Coin>) {
        coinsList.value = item
    }

    fun refresh() = viewModelScope.launch {
        coinsList.postValue(getDataCoins()!!)
    }
}

suspend fun getDataCoins(): List<Coin> {
    return RetrofitClient.coinGeckoApi.getCoins().body()!!
}

suspend fun getDataNews(): NewsResponse {
    return RetrofitClient.coinGeckoApi.getNews().body()!!
}


