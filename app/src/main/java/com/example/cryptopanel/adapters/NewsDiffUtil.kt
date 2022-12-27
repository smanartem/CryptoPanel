package com.example.cryptopanel.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptopanel.model.Article

class NewsDiffUtil : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}