package com.example.cryptopanel.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptopanel.model.Coin

class CoinDiffUtil : DiffUtil.ItemCallback<Coin>() {
    override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem == newItem
    }
}