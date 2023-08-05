package com.example.cryptopanel.ui.mainScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptopanel.R
import com.example.cryptopanel.ui.coinDetails.toFormat
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.utils.CoinDiffUtil
import com.example.cryptopanel.utils.setColor
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_currency.view.*

const val ID = "id"
const val NAME = "name"

class CryptoPanelListAdapter(private val onClickListener: (Int, String) -> Unit) :
    ListAdapter<Coin, CryptoPanelListAdapter.CoinViewHolder>(CoinDiffUtil()) {

    lateinit var topList: MutableSet<String>

    inner class CoinViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onClickListener(absoluteAdapterPosition, getItem(absoluteAdapterPosition).name)
            }

            itemView.check.setOnClickListener {
                checkOnClickListener(it.check, topList, getItem(absoluteAdapterPosition).id)
            }
        }

        fun bindTo(coin: Coin, position: Int) {
            with(itemView) {
                number.text = coin.market_cap_rank.toInt().toString()
                dayChange.text = buildString {
                    append("%.2f")
                }.format(coin.price_change_percentage_24h)
                dayChange.setTextColor(setColor(coin.price_change_percentage_24h))
                nameCoin.text = coin.name
                priceCoin.text = coin.current_price.toFormat()

                Picasso.get()
                    .load(coin.image)
                    .resize(100, 100)
                    .into(imageView)

                if (topList.contains(coin.id)) {
                    checkIsTrue(check)
                } else {
                    checkIsFalse(check)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        return CoinViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bindTo(getItem(position), position)
    }

    fun setListTop(set: MutableSet<String>) {
        topList = set
    }

    fun getListTop(): MutableSet<String> {
        return topList
    }
}

fun checkOnClickListener(check: CheckedTextView, topList: MutableSet<String>, id: String) {
    if (check.isChecked) {
        checkIsFalse(check)
        topList.remove(id)
    } else {
        checkIsTrue(check)
        topList.add(id)
    }
}

//TODO: funs below replace to class adapter and make them protected or private
fun checkIsTrue(check: CheckedTextView) {
    check.isChecked = true
    check.setCheckMarkDrawable(R.drawable.star_full)
}

fun checkIsFalse(check: CheckedTextView) {
    check.isChecked = false
    check.setCheckMarkDrawable(R.drawable.star_empty)
}