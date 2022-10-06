package com.example.cryptopanel.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptopanel.databinding.ItemArticleBinding
import com.example.cryptopanel.model.Article
import com.example.cryptopanel.model.NewsResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_article.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    private var news = listOf<Article>()

    inner class MyViewHolder(binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(news[position]) {
            holder.itemView.apply {
                tvSource.text = source.name
                tvTitle.text = title
                tvDescription.text = description
                tvPublishedAt.text = publishedAt

                Picasso.get()
                    .load(urlToImage)
                    .resize(100, 100)
                    .into(ivArticleImage)
            }
        }
    }

    override fun getItemCount(): Int = news.size

    fun setNews(breakingNews: NewsResponse) {
        this.news = breakingNews.articles
        notifyDataSetChanged()
    }
}