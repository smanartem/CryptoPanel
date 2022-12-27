package com.example.cryptopanel.model

data class Item(
    val id: String,
    val coin_id: Int,
    val name: String,
    val symbol: String,
    val market_cap_rank: Int,
    val thumb: String,
    val small: String,
    val large: String,
    val slug: String,
    val price_btc: Double,
    val score: Int
)