package com.example.cryptopanel

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.example.cryptopanel.databinding.ActivityMainBinding
import com.example.cryptopanel.viewModels.CryptoPanelViewModel

class MainActivity : AppCompatActivity() {
    val adapter = CryptoPanelAdapter(this)
    val model: CryptoPanelViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        val mDecoration = DividerItemDecoration(this, VERTICAL)
        mDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable))
        binding.recyclerView.addItemDecoration(mDecoration)

        setSupportActionBar(binding.toolbar)

        binding.progressBar.isVisible = true

        model.coinsList.observe(this) {
            adapter.setCoinsList(it)
            binding.progressBar.isVisible = false
        }
        model.getAllCoins()
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
                    val sortedCoins = sortByName(model.coinsList.value!!, query)
                    adapter.setCoinsList(sortedCoins)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    adapter.setCoinsList(model.coinsList.value!!)
                }
                searchAdapter.filter.filter(newText)
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}