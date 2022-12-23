package com.example.cryptopanel.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptopanel.model.NewsResponse
import com.example.cryptopanel.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// то же самое что и CryptoPanelViewModel
class NewsViewModel(app: Application) : AndroidViewModel(app) {

    // то же самое что и CryptoPanelViewModel
    val news = MutableLiveData<NewsResponse>()

    //"!!"
    fun getAllNews() = viewModelScope.launch(Dispatchers.IO) {
        news.postValue(getDataNews()!!)
    }

    fun getSearchNews(query: String) = viewModelScope.launch(Dispatchers.IO) {
        news.postValue(RetrofitClient.coinGeckoApi.getNews(searchQuery = query).body())
    }
}
// "!!" почему не внутри класса?
suspend fun getDataNews(): NewsResponse {
    return RetrofitClient.coinGeckoApi.getNews().body()!!
}