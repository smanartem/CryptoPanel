package com.example.cryptopanel

import com.example.cryptopanel.model.Coin

fun comparsion(startCoin: Coin, compareCoin: Coin, countStartCoin: Double): Double{
    val usdPrice = startCoin.current_price * countStartCoin

    return  usdPrice / compareCoin.current_price
}