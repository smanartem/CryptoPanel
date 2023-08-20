package com.example.cryptopanel.utils.menu

import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.example.cryptopanel.R
import com.example.cryptopanel.data.data.model.CoinDataModel
import com.example.cryptopanel.ui.mainScreen.CryptoPanelViewModel

fun createSearchView(menu: Menu, viewModel: CryptoPanelViewModel){
    val searchItem = menu.findItem(R.id.action_search)
    val searchView = searchItem.actionView as SearchView

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (query!!.isNotEmpty()) {
                val sortedArray = sortByName(viewModel.coinsListLive.value!!, query)
                viewModel.setSortArray(sortedArray)
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText.isNullOrEmpty()) viewModel.refresh()
            return true
        }
    })
}

private fun sortByName(items: List<CoinDataModel>, chars: String): List<CoinDataModel> {
    val tempArray = mutableListOf<CoinDataModel>()
    items.forEach {
        if (it.name.lowercase().contains(chars.lowercase())) {
            tempArray.add(it)
        }
    }
    return tempArray
}