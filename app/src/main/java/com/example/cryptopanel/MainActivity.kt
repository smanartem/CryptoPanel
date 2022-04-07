package com.example.cryptopanel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.recyclerview.widget.*
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.retrofit.RetrofitClient
import com.example.cryptopanel.retrofit.RetrofitServieces
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview: RecyclerView = findViewById(R.id.recycler_view)
        recyclerview.layoutManager = LinearLayoutManager(this)

        // Add SearchView
        val searchView: SearchView = findViewById(R.id.searchView)

        val progress: ProgressBar = findViewById(R.id.progressBar)
        val logo: ImageView = findViewById(R.id.logo)
        logo.isVisible = true
        progress.isVisible = true

        var coins: List<Coin> = listOf()

        //  Getting data in parallel thread by coroutines
        MainScope().launch {
            coins = getData()
            setItems(coins, recyclerview, applicationContext)
            progress.isVisible = false
        }
// Setting up SearchBar
        val searchAdapter = ArrayAdapter<String>(this, R.layout.recyclerview_item)
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchAdapter.filter.filter(query)

                if (query!!.isNotEmpty()) {
                    val sortedCoins = sortByName(coins, query)
                    setItems(sortedCoins, recyclerview, applicationContext)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    setItems(coins, recyclerview, applicationContext)
                }
                searchAdapter.filter.filter(newText)
                return false
            }
        })

        recyclerview.adapter = CryptoPanelAdapter(applicationContext, coins)
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

fun sortByName(items: List<Coin>, chars: String): List<Coin> {
    val tempArray = mutableListOf<Coin>()
    items.forEach {
        if (it.name.lowercase().contains(chars.lowercase())) {
            tempArray.add(it)
        }
    }
    return tempArray
}