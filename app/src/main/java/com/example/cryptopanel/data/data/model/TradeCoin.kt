package com.example.cryptopanel.data.data.model

import java.io.Serializable

data class TradeCoin(
    val ethereum: Ethereum
)

data class Ethereum(
    val usd: Double,
    val usd_24h_change: Double
)