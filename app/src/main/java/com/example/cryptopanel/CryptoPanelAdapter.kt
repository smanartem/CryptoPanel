package com.example.cryptopanel

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptopanel.model.Coin

class CryptoPanelAdapter(private val context: Context, private val coins: List<Coin>) :
    RecyclerView.Adapter<CryptoPanelAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameCoin: TextView = view.findViewById(R.id.nameCoin)
        val priceCoin: TextView = view.findViewById(R.id.priceCoin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameCoin.text = coins[position].name
        holder.priceCoin.text = "%.2f".format(coins[position].current_price)
        holder.nameCoin.setOnClickListener({
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra("c", coins[position])
            holder.nameCoin.context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return if (coins.isNotEmpty()) coins.size else 0
    }
}