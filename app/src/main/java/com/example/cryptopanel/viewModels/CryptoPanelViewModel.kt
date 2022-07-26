package com.example.cryptopanel.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoPanelViewModel(application: Application) : AndroidViewModel(application) {
    var coinsList = MutableLiveData<List<Coin>>()

    fun getAllCoins() {

        if (coinsList.value.isNullOrEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                coinsList.postValue(getData()!!)
            }
        }
    }
}

suspend fun getData(): List<Coin> {
    return RetrofitClient.coinGeckoApi.getCoins().body()!!
}
