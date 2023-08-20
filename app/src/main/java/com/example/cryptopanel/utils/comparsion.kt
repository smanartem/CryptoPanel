package com.example.cryptopanel.utils

import com.example.cryptopanel.data.data.model.CoinDataModel

fun comparsion(startCoinDataModel: CoinDataModel, compareCoinDataModel: CoinDataModel, countStartCoin: Double): Double{
    val usdPrice = startCoinDataModel.current_price * countStartCoin

    return  usdPrice / compareCoinDataModel.current_price
}