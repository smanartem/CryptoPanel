package com.example.cryptopanel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.*
import com.example.cryptopanel.Model.Coin
import com.example.cryptopanel.Retrofit.RetrofitClient
import com.example.cryptopanel.Retrofit.RetrofitServieces
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview: RecyclerView = findViewById(R.id.recycler_view)
        recyclerview.layoutManager = LinearLayoutManager(this)

        val progress: ProgressBar = findViewById(R.id.progressBar)
        val logo: ImageView = findViewById(R.id.logo)
        logo.isVisible = true
        progress.isVisible = true

        val coinsEmpty: List<Coin> = listOf()

        // 1. Getting data in parallel thread
        MainScope().launch {
            val coinsFull = getData()
            setItems(coinsFull, recyclerview, applicationContext)
            progress.isVisible = false
        }
        recyclerview.adapter = CryptoPanelAdapter(applicationContext, coinsEmpty)
    }
}

suspend fun getData(): List<Coin> {
    val retrofitService: RetrofitServieces = RetrofitClient.getClient()
        .create(RetrofitServieces::class.java)
    val response = retrofitService.getCoins().body()
    // imitation long time work
    delay(2000)
    return response!!
}

// 2. Set full list of coins in adapter
fun setItems(items: List<Coin>, recyclerView: RecyclerView, context: Context) {
    recyclerView.adapter = CryptoPanelAdapter(context, items)
    recyclerView.adapter!!.notifyDataSetChanged()
}
