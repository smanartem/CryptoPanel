package com.example.cryptopanel.adapters

fun doubleToString(price: Double): String {
    return if (price < 1) "%.6f".format(price) else "%.2f".format(price)
}