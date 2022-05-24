package com.example.cryptopanel

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptopanel.model.Coin
import com.robinhood.spark.SparkAdapter
import com.robinhood.spark.SparkView

class CryptoPanelAdapter(private val context: Context) :
    RecyclerView.Adapter<CryptoPanelAdapter.MyViewHolder>() {

    private var coins = listOf<Coin>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameCoin: TextView = view.findViewById(R.id.nameCoin)
        val priceCoin: TextView = view.findViewById(R.id.priceCoin)
        val dayChange: TextView = view.findViewById(R.id.dayChange)
        val sparkRecycler: SparkView = view.findViewById(R.id.spark_in_recycler)
    }

    fun setCoinsList(coins: List<Coin>) {
        this.coins = coins
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val adapter: SparkAdapter = SparklineAdapter(coins[position].sparkline_in_7d.price)
        holder.sparkRecycler.adapter = adapter
        holder.sparkRecycler.lineColor = setColor(coins[position].price_change_24h)

        holder.dayChange.text = "%.2f".format(coins[position].price_change_24h)
        holder.dayChange.setTextColor(setColor(coins[position].price_change_24h))
        holder.nameCoin.text = coins[position].name
        holder.priceCoin.text = "%.2f".format(coins[position].current_price)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra("key", coins[position])
            holder.nameCoin.context.startActivity(intent)
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