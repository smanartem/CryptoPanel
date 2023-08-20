package com.example.cryptopanel.domain

import com.example.cryptopanel.data.data.model.CoinDataModel
import com.example.cryptopanel.ui.model.CoinUiModel

fun List<CoinDataModel>.mapToListUiModels(): List<CoinUiModel> =
    this.map {
        CoinUiModel(
            id = it.id,
            name = it.name,
            price = it.current_price,
            dayChange = it.price_change_24h,
            imageUrl = it.image,
            marketCap = it.market_cap,
            marketCapRank = it.market_cap_rank,
            totalSupply = it.total_supply,
            high24h = it.high_24h,
            low24h = it.low_24h,
            sparkline = it.sparkline_in_7d.price
        )
    }