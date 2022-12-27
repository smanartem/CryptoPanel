package com.example.cryptopanel.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptopanel.model.Article
import com.example.cryptopanel.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(app: Application) : AndroidViewModel(app) {
    val news = MutableLiveData<List<Article>>()

    fun getAllNews() = viewModelScope.launch {
        news.postValue(getDataNews())
    }

    fun getSearchNews(query: String) = viewModelScope.launch(Dispatchers.IO) {
        news.postValue(RetrofitClient.coinGeckoApi.getNews(searchQuery = query).body()?.articles)
    }

    private suspend fun getDataNews() = RetrofitClient.coinGeckoApi.getNews().body()?.articles
}