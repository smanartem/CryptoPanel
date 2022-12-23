package com.example.cryptopanel.viewModels

import com.example.cryptopanel.model.TrendCoin

//не похоже на ViewModel, почему тогда в папке viewModels?)
//для таких вещей лучше создать отдельную папку utils
fun extractString(array: List<TrendCoin>): String {
    val names = mutableListOf<String>()
    for (i in array) {
        names.add(i.item.id)
    }
    return listToString(names)
}

fun listToString(array: List<String>): String {
    val baseElement = ", "
    var result = ""
    for (index in array.indices) {
        // можно просто array[index].lowercase()
        result += "${array[index]}".lowercase()
        // можно просто baseElement.lowercase()
        if (index != array.lastIndex) result += "$baseElement".lowercase()
    }
    return result
}