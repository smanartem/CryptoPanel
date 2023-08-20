package com.example.cryptopanel.ui.mainScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptopanel.data.data.retrofit.CoinGeckoApi
import com.example.cryptopanel.data.data.model.CoinDataModel
import com.example.cryptopanel.utils.extractListOfString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoPanelViewModel(private val coinGeckoApi: CoinGeckoApi, app: Application) :
    AndroidViewModel(app) {

    private var listOfCoinDataModels: List<CoinDataModel> = listOf()

    private val coinsListMutable = MutableLiveData<List<CoinDataModel>>()
    val coinsListLive: LiveData<List<CoinDataModel>> = coinsListMutable

    fun loadCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            setDataToList(coinGeckoApi.getCoins().body() ?: emptyList())
            refresh()
        }
    }

    private fun setDataToList(data: List<CoinDataModel>) {
        listOfCoinDataModels = data
    }

    fun setSortArray(item: List<CoinDataModel>) {
        coinsListMutable.postValue(item)
    }

    fun refresh() {
        coinsListMutable.postValue(listOfCoinDataModels)
    }

    fun getTrend() = viewModelScope.launch {
        coinsListMutable.postValue(filterIt(getTrendingCoinsString(), listOfCoinDataModels))
    }

    fun getTop(array: List<String>) = viewModelScope.launch {
        coinsListMutable.postValue(filterIt(array, listOfCoinDataModels))
    }

    private suspend fun getTrendingCoinsString(): List<String> {
        return extractListOfString(coinGeckoApi.getTrendCoins().body()?.coins)
    }


    fun getData(): List<CoinDataModel> {
        return listOfCoinDataModels
    }

    fun filterIt(s: List<String>, list: List<CoinDataModel>): List<CoinDataModel> {
        val tempArray = mutableListOf<CoinDataModel>()

        s.forEach {
            for (l in list) {
                if (l.id == it) tempArray.add(l)
            }
        }
        return tempArray
    }
}