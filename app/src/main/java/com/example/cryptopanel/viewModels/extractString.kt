package com.example.cryptopanel.viewModels

import com.example.cryptopanel.model.TrendCoin

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
        result += "${array[index]}".lowercase()
        if (index != array.lastIndex) result += "$baseElement".lowercase()
    }
    return result
}