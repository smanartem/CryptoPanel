package com.example.cryptopanel

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.example.cryptopanel.viewModels.CryptoPanelViewModel

class MainActivity : AppCompatActivity() {
    val adapter = CryptoPanelAdapter(this)
    val model: CryptoPanelViewModel by viewModels()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview: RecyclerView = findViewById(R.id.recycler_view)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
        recyclerview.setHasFixedSize(true)

        val mDecoration = DividerItemDecoration(this, VERTICAL)
        mDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable))
        recyclerview.addItemDecoration(mDecoration)

        setSupportActionBar(findViewById(R.id.toolbar))

        val progress: ProgressBar = findViewById(R.id.progressBar)
        progress.isVisible = true

        model.coinsList.observe(this) {
            Log.d("TAG", "Observer started")
            adapter.setCoinsList(it)
            progress.isVisible = false
        }
         model.getAllCoins()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_resource, menu)

        // SearchView
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