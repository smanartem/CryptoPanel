package com.example.cryptopanel.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptopanel.model.Article
import com.example.cryptopanel.retrofit.CoinGeckoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NewsViewModel(private val coinGeckoApi: CoinGeckoApi) : ViewModel() {
    private val news = MutableLiveData<List<Article>>()
    val _news: LiveData<List<Article>> = news

    fun getAllNews() = viewModelScope.launch {
        news.postValue(getDataNews())
    }

    fun getSearchNews(query: String) = viewModelScope.launch(Dispatchers.IO) {
        news.postValue(coinGeckoApi.getNews(searchQuery = query).body()?.articles)
    }

    private suspend fun getDataNews() = coinGeckoApi.getNews().body()?.articles
}