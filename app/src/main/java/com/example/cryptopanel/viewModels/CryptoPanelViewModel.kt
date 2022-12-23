package com.example.cryptopanel.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Для таких вещей нужно юзать DI и даже если не юзать DI почему не внутри ViewModel и не private?
val retrofit = RetrofitClient.coinGeckoApi

//почему AndroidViewModel а не просто ViewModel?
class CryptoPanelViewModel(app: Application) : AndroidViewModel(app) {

    //MutableLiveData не должна торчать из ViewModel она должа быть приватной и меняться только внутри вьюмодели
    //а доступ к ней должен быть через просто LiveData пример ниже
    //    private val _coinsList = MutableLiveData<List<Coin>>()
    //    val coinsList : LiveData<List<Coin>> = _coinsList
    val coinsList = MutableLiveData<List<Coin>>()

    //"!!"
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

    //fun refresh() = viewModelScope.launch {..}
    //"!!"
    fun refresh() = viewModelScope.launch(Dispatchers.IO) {
        coinsList.postValue(getDataCoins()!!)
    }

    //fun getTrend() = viewModelScope.launch {..}
    fun getTrend() {
        viewModelScope.launch {
            coinsList.postValue(retrofit.getTrendTopCoins(getTrendingCoinsString()).body())
        }
    }

    //fun getTop(s: String) = viewModelScope.launch {..}
    fun getTop(s: String) {
        viewModelScope.launch {
            coinsList.postValue(retrofit.getTrendTopCoins(s).body())
        }
    }
}

//Почему эти три функции не внутри ViewModel?


//Это можно написать в одну строчку просто suspend fun getDataCoins(): List<Coin> = retrofit.getCoins().body()!!
//!! - плохая практика использовать эту штуку, тем более во вью моделях, в этом случае можно вернуть emptyList()
//через элвис оператор
// в итого получится - getDataCoins(): List<Coin> = retrofit.getCoins().body() ?: emptyList()
suspend fun getDataCoins(): List<Coin> {
    return retrofit.getCoins().body()!!
}

//в одну строчку
fun getTopCoinsString(array: List<String>): String {
    return listToString(array)
}

//в одну строчку и "!!"
suspend fun getTrendingCoinsString(): String {
    return extractString(retrofit.getTrendCoins().body()!!.coins)
}

//лишние строки в конце класса



