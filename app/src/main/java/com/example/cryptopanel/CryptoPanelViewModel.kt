package com.example.cryptopanel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.retrofit.RetrofitClient
import com.example.cryptopanel.retrofit.RetrofitServieces
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoPanelViewModel : ViewModel() {
    var coinsList = MutableLiveData<List<Coin>>()

    fun getAllCoins() {
        CoroutineScope(Dispatchers.IO).launch {
            coinsList.postValue(getData()!!)
        }
    }

}

suspend fun getData(): List<Coin> {
    val retrofitService: RetrofitServieces = RetrofitClient.getClient()
        .create(RetrofitServieces::class.java)
    val response = retrofitService.getCoins().body()
    return response!!
}
