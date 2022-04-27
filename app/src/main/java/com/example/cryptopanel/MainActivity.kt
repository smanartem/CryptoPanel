package com.example.cryptopanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import com.example.cryptopanel.model.Coin


class MainActivity : AppCompatActivity() {
    val adapter = CryptoPanelAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview: RecyclerView = findViewById(R.id.recycler_view)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter

        val searchView: SearchView = findViewById(R.id.searchView)

        val progress: ProgressBar = findViewById(R.id.progressBar)
        progress.isVisible = true

        val vm = CryptoPanelViewModel()
        vm.coinsList.observe(this, Observer {
            adapter.setCoinsList(it)
            progress.isVisible = false
        })
        vm.getAllCoins()

        val searchAdapter = ArrayAdapter<String>(this, R.layout.recyclerview_item)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchAdapter.filter.filter(query)
                if (query!!.isNotEmpty()) {
                    val sortedCoins = sortByName(vm.coinsList.value!!, query)
                    adapter.setCoinsList(sortedCoins)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                adapter.setCoinsList(vm.coinsList.value!!)
                }
                searchAdapter.filter.filter(newText)
                return false
            }
        })
    }
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