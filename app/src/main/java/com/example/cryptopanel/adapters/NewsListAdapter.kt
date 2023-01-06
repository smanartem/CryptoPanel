package com.example.cryptopanel.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptopanel.R
import com.example.cryptopanel.databinding.ItemArticleBinding
import com.example.cryptopanel.model.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_article.view.*

const val URL = "url"
const val TITLE = "title"

class NewsListAdapter : ListAdapter<Article, NewsListAdapter.MyViewHolder>(NewsDiffUtil()) {

    inner class MyViewHolder(binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(news: Article) {
            with(itemView) {
                tvSource.text = news.source.name
                tvTitle.text = news.title
                tvDescription.text = news.description
                tvPublishedAt.text = news.publishedAt

                Picasso.get()
                    .load(news.urlToImage)
                    .resize(100, 100)
                    .into(ivArticleImage)

                setOnClickListener { view ->
                    view.findNavController().navigate(
                        R.id.action_fragmentNews_to_fragmentNewsDetails,
                        bundleOf(URL to news.url, TITLE to news.title)
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }
}