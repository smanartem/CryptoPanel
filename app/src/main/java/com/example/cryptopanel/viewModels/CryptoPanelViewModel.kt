package com.example.cryptopanel.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.retrofit.RetrofitClient
import kotlinx.coroutines.*

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
}

suspend fun getDataCoins(): List<Coin> {
    return RetrofitClient.coinGeckoApi.getCoins().body()!!
}


