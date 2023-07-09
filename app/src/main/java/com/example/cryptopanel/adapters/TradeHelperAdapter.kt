package com.example.cryptopanel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptopanel.R
import com.example.cryptopanel.fragments.FIRST_PRICE
import com.example.cryptopanel.fragments.START_CURRENCY
import com.example.cryptopanel.fragments.START_SYMBOL
import com.example.cryptopanel.fragments.toFormat
import com.example.cryptopanel.model.Coin
import kotlinx.android.synthetic.main.item_template.view.*

class TradeHelperAdapter(private val onClickListener: (String, Double, Double) -> Unit) :
    RecyclerView.Adapter<TradeHelperAdapter.MyViewHolder>() {

    private var filteredList: List<Coin> = listOf()
    private var summaUsd: Double = FIRST_PRICE

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal fun bindTo(item: Coin) {
            with(itemView) {
                name_coin.text = item.id

                val quantity = summaUsd / item.current_price
                count.text = quantity.toFormat()

                switch_btn.setOnClickListener {
                    onClickListener(item.id, quantity, item.current_price)
                    changeSumma(coinToUsd(item.current_price, quantity))
                }
            }
        }

        internal fun bindFirstItem() {
            with(itemView) {
                name_coin.text = START_SYMBOL
                count.text = summaUsd.toFormat()

                switch_btn.setOnClickListener {
                    onClickListener(START_SYMBOL, summaUsd, START_CURRENCY)
                    changeSumma(coinToUsd(START_CURRENCY, summaUsd))
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_template, parent, false)
        return MyViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position == 0) holder.bindFirstItem() else holder.bindTo(filteredList[position])
    }

    override fun getItemCount() = filteredList.size

    fun setFilteredList(list: List<Coin>, price: Double) {
        filteredList = list
        summaUsd = price
        notifyDataSetChanged()
    }

    fun changeSumma(newSumma: Double) {
        summaUsd = newSumma
        notifyDataSetChanged()
    }

    fun coinToUsd(price: Double, quantity: Double): Double {
        return price * quantity
    }

}