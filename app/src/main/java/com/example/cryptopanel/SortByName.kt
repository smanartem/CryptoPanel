package com.example.cryptopanel

import com.example.cryptopanel.model.Coin

//не понянто почему это в руте рядом с App классом, а не там где это исспользуется
// и зачем отдельным файлом
fun sortByName(items: List<Coin>, chars: String): List<Coin> {
    val tempArray = mutableListOf<Coin>()
    items.forEach {
        if (it.name.lowercase().contains(chars.lowercase())) {
            tempArray.add(it)
        }
    }
    return tempArray

}