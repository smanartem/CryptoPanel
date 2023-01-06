package com.example.cryptopanel.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptopanel.R
import com.example.cryptopanel.databinding.ItemCurrencyBinding
import com.example.cryptopanel.fragments.toFormat
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.utils.setColor
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_currency.view.*
const val ID = "id"
const val NAME = "name"

class CryptoPanelListAdapter :
    ListAdapter<Coin, CryptoPanelListAdapter.MyViewHolder>(CoinDiffUtil()) {

    lateinit var topList: MutableSet<String>

    inner class MyViewHolder(binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(coin: Coin, position: Int) {
            with(itemView) {
                number.text = "#${position + 1}"
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

                setOnClickListener { view ->
                    view.findNavController().navigate(
                        R.id.action_fragmentMain_to_fragmentCoinDetails,
                        bundleOf(ID to position, NAME to coin.name)
                    )
                }

                if (topList.contains(coin.id)) {
                    checkIsTrue(check)
                } else {
                    checkIsFalse(check)
                }

                check.setOnClickListener {
                    checkOnClickListener(it.check, topList, coin.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
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

fun checkIsTrue(check: CheckedTextView) {
    check.isChecked = true
    check.setCheckMarkDrawable(R.drawable.star_full)
}

fun checkIsFalse(check: CheckedTextView) {
    check.isChecked = false
    check.setCheckMarkDrawable(R.drawable.star_empty)
}