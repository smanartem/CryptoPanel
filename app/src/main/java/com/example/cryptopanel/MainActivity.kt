package com.example.cryptopanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import com.example.cryptopanel.model.Coin


class MainActivity : AppCompatActivity() {
    val adapter = CryptoPanelAdapter(this)
    val viewModel = CryptoPanelViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview: RecyclerView = findViewById(R.id.recycler_view)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter

        setSupportActionBar(findViewById(R.id.toolbar))

        val progress: ProgressBar = findViewById(R.id.progressBar)
        progress.isVisible = true

        viewModel.coinsList.observe(this, Observer {
            adapter.setCoinsList(it)
            progress.isVisible = false
        })
        viewModel.getAllCoins()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_resource, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        val searchAdapter = ArrayAdapter<String>(this, R.layout.recyclerview_item)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchAdapter.filter.filter(query)
                if (query!!.isNotEmpty()) {
                    val sortedCoins = sortByName(viewModel.coinsList.value!!, query)
                    adapter.setCoinsList(sortedCoins)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    adapter.setCoinsList(viewModel.coinsList.value!!)
                }
                searchAdapter.filter.filter(newText)
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

}