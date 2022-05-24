package com.example.cryptopanel.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.retrofit.RetrofitClient
import com.example.cryptopanel.retrofit.RetrofitServieces
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoPanelViewModel(application: Application) : AndroidViewModel(application) {
    var coinsList = MutableLiveData<List<Coin>>()

    fun getAllCoins() {

        if (coinsList.value.isNullOrEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("TAG", "Coroutine started")
                coinsList.postValue(getData()!!)
            }
        }
    }

}

suspend fun getData(): List<Coin> {
    val retrofitService: RetrofitServieces = RetrofitClient.getClient()
        .create(RetrofitServieces::class.java)
    val response = retrofitService.getCoins().body()
    return response!!
}
