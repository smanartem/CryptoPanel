package com.example.cryptopanel.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptopanel.ui.model.CoinUiModel

class CoinDiffUtil : DiffUtil.ItemCallback<CoinUiModel>() {
    override fun areItemsTheSame(oldItem: CoinUiModel, newItem: CoinUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CoinUiModel, newItem: CoinUiModel): Boolean {
        return oldItem == newItem
    }
}