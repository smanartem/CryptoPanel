package com.example.cryptopanel.utils

import com.example.cryptopanel.model.TrendCoin

fun extractListOfString(array: List<TrendCoin>?): List<String> {
    return if (array != null) {
        val names = mutableListOf<String>()
        for (i in array) {
            names.add(i.item.id)
        }
        names
    } else {
        val emptyList: List<String> = listOf()
        emptyList
    }
}