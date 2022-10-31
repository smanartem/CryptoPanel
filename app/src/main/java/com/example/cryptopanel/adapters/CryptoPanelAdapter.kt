package com.example.cryptopanel.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptopanel.MyDiffUtil
import com.example.cryptopanel.R
import com.example.cryptopanel.databinding.ItemCurrencyBinding
import com.example.cryptopanel.model.Coin
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_currency.view.*

class CryptoPanelAdapter : RecyclerView.Adapter<CryptoPanelAdapter.MyViewHolder>() {

    private val differ = AsyncListDiffer(this, MyDiffUtil())

    inner class MyViewHolder(binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root)

    fun setCoinsList(newCoins: List<Coin>) {
        differ.submitList(newCoins)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            with(differ.currentList[position]) {
                holder.itemView.number.text = buildString {
                    append("#")
                    append(position + 1)
                }
                itemView.dayChange.text = buildString {
                    append("%.2f")
                }.format(this.price_change_percentage_24h)
                itemView.dayChange.setTextColor(setColor(this.price_change_percentage_24h))
                itemView.nameCoin.text = this.name
                itemView.priceCoin.text = doubleToString(this.current_price)
                itemView.setOnClickListener {view ->
                    view.findNavController().navigate(R.id.action_fragmentMain_to_fragmentCoinDetails,
                    bundleOf("id" to position, "name" to this.name))
                }

                Picasso.get()
                    .load(this.image)
                    .resize(80, 80)
                    .into(itemView.imageView)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}

fun setColor(x: Double): Int {
    return if (x > 0) {
        Color.GREEN
    } else {
        Color.RED
    }
}

fun doubleToString(price: Double): String {
    return if (price < 1) "%.6f".format(price) else "%.2f".format(price)
}