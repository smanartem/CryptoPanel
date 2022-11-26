package com.example.cryptopanel.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val retrofit = RetrofitClient.coinGeckoApi

class CryptoPanelViewModel(app: Application) : AndroidViewModel(app) {
    val coinsList = MutableLiveData<List<Coin>>()

    fun getAllCoins() {
        if (coinsList.value.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                coinsList.postValue(getDataCoins()!!)
            }
        }
    }

    fun setSortArray(item: List<Coin>) {
        coinsList.value = item
    }

    fun refresh() = viewModelScope.launch(Dispatchers.IO) {
        coinsList.postValue(getDataCoins()!!)
    }

    fun getTrend() {
        viewModelScope.launch {
            coinsList.postValue(retrofit.getTrendTopCoins(getTrendingCoinsString()).body())
        }
    }

    fun getTop(s: String) {
        viewModelScope.launch {
            coinsList.postValue(retrofit.getTrendTopCoins(s).body())
        }
    }
}

suspend fun getDataCoins(): List<Coin> {
    return retrofit.getCoins().body()!!
}

fun getTopCoinsString(array: List<String>): String {
    return listToString(array)
}

suspend fun getTrendingCoinsString(): String {
    return extractString(retrofit.getTrendCoins().body()!!.coins)
}




