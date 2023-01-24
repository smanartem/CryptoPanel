package com.example.cryptopanel.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.retrofit.CoinGeckoApi
import com.example.cryptopanel.utils.extractString
import com.example.cryptopanel.utils.listToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoPanelViewModel(private val coinGeckoApi: CoinGeckoApi, app: Application) : AndroidViewModel(app) {
    private val coinsList = MutableLiveData<List<Coin>>()
    val _coinsList: LiveData<List<Coin>> = coinsList


    fun getAllCoins() {
        if (coinsList.value.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                coinsList.postValue(getDataCoins())
            }
        }
    }

    fun setSortArray(item: List<Coin>) {
        coinsList.value = item
    }

    fun refresh() = viewModelScope.launch {
        coinsList.postValue(getDataCoins())
    }

    fun getTrend() = viewModelScope.launch {
        coinsList.postValue(coinGeckoApi.getTrendTopCoins(getTrendingCoinsString()).body())
    }


    fun getTop(array: List<String>) = viewModelScope.launch {
        val s = getTopCoinsString(array)
        coinsList.postValue(coinGeckoApi.getTrendTopCoins(s).body())
    }

    private suspend fun getDataCoins() = coinGeckoApi.getCoins().body() ?: emptyList()


    private fun getTopCoinsString(array: List<String>): String {
        return listToString(array)
    }

    private suspend fun getTrendingCoinsString(): String {
        return extractString(coinGeckoApi.getTrendCoins().body()?.coins)
    }
}