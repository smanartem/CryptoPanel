package com.example.cryptopanel.menu

import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.example.cryptopanel.R
import com.example.cryptopanel.sortByName
import com.example.cryptopanel.viewModels.CryptoPanelViewModel

//передовать ViewModel в такие функции (да и куда либо в принципе) такое себе
//лучше делегирывать логику через лямбды например
//опять же, непонятно почеум это хайлэвл функция, а не приватная функция какого-то фрагмента или активити
fun createSearchView(menu: Menu, viewModel: CryptoPanelViewModel){
    val searchItem = menu.findItem(R.id.action_search)
    val searchView = searchItem.actionView as SearchView

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (query!!.isNotEmpty()) {
                val sortedArray = sortByName(viewModel.coinsList.value!!, query)
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