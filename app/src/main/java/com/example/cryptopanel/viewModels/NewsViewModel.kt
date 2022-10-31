package com.example.cryptopanel.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptopanel.model.NewsResponse
import com.example.cryptopanel.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(app: Application) : AndroidViewModel(app) {
    val news = MutableLiveData<NewsResponse>()

    fun getAllNews() = viewModelScope.launch(Dispatchers.IO) {
        news.postValue(getDataNews()!!)
    }

    fun getSearchNews(query: String) = viewModelScope.launch(Dispatchers.IO) {
        news.postValue(RetrofitClient.coinGeckoApi.getNews(searchQuery = query).body())
    }
}

suspend fun getDataNews(): NewsResponse {
    return RetrofitClient.coinGeckoApi.getNews().body()!!
}