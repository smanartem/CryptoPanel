package com.example.cryptopanel.ui.model

data class CoinUiModel(
    val id: String,
    val name: String,
    val price: Double,
    val dayChange: Double,
    val imageUrl: String,
    val marketCap: Double,
    val marketCapRank: Double,
    val totalSupply: Double,
    val high24h: Double,
    val low24h: Double,
    val sparkline: ArrayList<Double>
)