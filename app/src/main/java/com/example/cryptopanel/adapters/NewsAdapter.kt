package com.example.cryptopanel.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptopanel.R
import com.example.cryptopanel.databinding.ItemArticleBinding
import com.example.cryptopanel.model.Article
import com.example.cryptopanel.model.NewsResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_article.view.*

//ListAdapter
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    private var news = listOf<Article>()

    //то же что и в CryptoPanelAdapter
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
                        //мэджык намберс
                    .resize(100, 100)
                    .into(ivArticleImage)
            }
            holder.itemView.setOnClickListener{view ->
                view.findNavController().navigate(R.id.action_fragmentNews_to_fragmentNewsDetails,
                bundleOf("url" to url, "title" to title))
            }
        }
    }

    override fun getItemCount(): Int = news.size

    fun setNews(breakingNews: NewsResponse) {
        this.news = breakingNews.articles
        //будет каждый раз проходится по всему списку, лучше использовать более специфические методы типа
        //notifyItemChanged(position = Id) notifyItemRemoved(position = Id) и тд, а лучше заюзать ListAdapter
        //который будет делать всё это за тебя
        notifyDataSetChanged()
    }
}