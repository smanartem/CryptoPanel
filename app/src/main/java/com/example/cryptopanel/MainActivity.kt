package com.example.cryptopanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cryptopanel.databinding.ActivityMainBinding
import com.example.cryptopanel.viewModels.CryptoPanelViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val model: CryptoPanelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val pagerAdapter = MainActivityPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.currentItem = 0
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = if (position == 0) {
                "Coins"
            } else {
                "News"
            }
        }.attach()
    }

    private inner class MainActivityPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return if (position == 0) {
                FragmentMain()
            } else {
                FragmentNews()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_resource, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.isNotEmpty()) {
                    val sortedArray = sortByName(model.coinsList.value!!, query)
                    model.setSortArray(sortedArray)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) model.refresh()
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}