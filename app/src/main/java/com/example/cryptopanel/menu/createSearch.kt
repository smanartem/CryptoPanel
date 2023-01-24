package com.example.cryptopanel.menu

import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.example.cryptopanel.R
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.viewModels.CryptoPanelViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

fun createSearchView(menu: Menu, viewModel: CryptoPanelViewModel){
    val searchItem = menu.findItem(R.id.action_search)
    val searchView = searchItem.actionView as SearchView

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (query!!.isNotEmpty()) {
                val sortedArray = sortByName(viewModel._coinsList.value!!, query)
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

private fun sortByName(items: List<Coin>, chars: String): List<Coin> {
    val tempArray = mutableListOf<Coin>()
    items.forEach {
        if (it.name.lowercase().contains(chars.lowercase())) {
            tempArray.add(it)
        }
    }
    return tempArray
}