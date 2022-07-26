package com.example.cryptopanel

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptopanel.databinding.RecyclerviewItemBinding
import com.example.cryptopanel.model.Coin
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class CryptoPanelAdapter(private val context: Context) :
    RecyclerView.Adapter<CryptoPanelAdapter.MyViewHolder>() {

    private var coins = listOf<Coin>()

    inner class MyViewHolder(binding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setCoinsList(coins: List<Coin>) {
        this.coins = coins
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            with(coins[position]) {
                itemView.number.text = buildString {
                    append("#")
                    append(position + 1)
                }
                itemView.dayChange.text = "%.2f".format(this.price_change_24h)
                itemView.dayChange.setTextColor(setColor(this.price_change_24h))
                itemView.nameCoin.text = this.name
                itemView.priceCoin.text = "%.2f".format(this.current_price)
                itemView.setOnClickListener {
                    val intent = Intent(context, SecondActivity::class.java)
                    intent.putExtra("key", this)
                    itemView.nameCoin.context.startActivity(intent)
                }

                Picasso.get()
                    .load(this.image)
                    .resize(80, 80)
                    .into(itemView.imageView)
            }
        }
    }

    override fun getItemCount(): Int {
        return coins.size
    }

}

fun setColor(x: Double): Int {
    return if (x > 0) {
        Color.GREEN
    } else {
        Color.RED
    }
}