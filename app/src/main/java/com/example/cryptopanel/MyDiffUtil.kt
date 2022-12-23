package com.example.cryptopanel

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptopanel.model.Coin

//не понятно почему в руте, а не в папке с адаптером для которого этот дифутилс написан
class MyDiffUtil: DiffUtil.ItemCallback<Coin>(){

    override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
       return oldItem == newItem
    }
}