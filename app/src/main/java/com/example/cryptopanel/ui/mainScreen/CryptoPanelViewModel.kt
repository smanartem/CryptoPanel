package com.example.cryptopanel.ui.mainScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptopanel.data.retrofit.CoinGeckoApi
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.utils.extractListOfString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoPanelViewModel(private val coinGeckoApi: CoinGeckoApi, app: Application) :
    AndroidViewModel(app) {

    private var listOfCoins: List<Coin> = listOf()

    private val coinsListMutable = MutableLiveData<List<Coin>>()
    val coinsListLive: LiveData<List<Coin>> = coinsListMutable

    fun loadCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            setDataToList(coinGeckoApi.getCoins().body() ?: emptyList())
            refresh()
        }
    }

    private fun setDataToList(data: List<Coin>) {
        listOfCoins = data
    }

    fun setSortArray(item: List<Coin>) {
        coinsListMutable.postValue(item)
    }

    fun refresh() {
        coinsListMutable.postValue(listOfCoins)
    }

    fun getTrend() = viewModelScope.launch {
        coinsListMutable.postValue(filterIt(getTrendingCoinsString(), listOfCoins))
    }

    fun getTop(array: List<String>) = viewModelScope.launch {
        coinsListMutable.postValue(filterIt(array, listOfCoins))
    }

    private suspend fun getTrendingCoinsString(): List<String> {
        return extractListOfString(coinGeckoApi.getTrendCoins().body()?.coins)
    }


    fun getData(): List<Coin> {
        return listOfCoins
    }

    fun filterIt(s: List<String>, list: List<Coin>): List<Coin> {
        val tempArray = mutableListOf<Coin>()

        s.forEach {
            for (l in list) {
                if (l.id == it) tempArray.add(l)
            }
        }
        return tempArray
    }
}