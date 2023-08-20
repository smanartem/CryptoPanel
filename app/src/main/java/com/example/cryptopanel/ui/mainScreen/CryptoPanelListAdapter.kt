package com.example.cryptopanel.ui.mainScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptopanel.R
import com.example.cryptopanel.ui.model.CoinUiModel
import com.example.cryptopanel.utils.CoinDiffUtil
import com.example.cryptopanel.utils.setColor
import com.example.cryptopanel.utils.toFormat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_currency.view.check
import kotlinx.android.synthetic.main.item_currency.view.dayChange
import kotlinx.android.synthetic.main.item_currency.view.imageView
import kotlinx.android.synthetic.main.item_currency.view.nameCoin
import kotlinx.android.synthetic.main.item_currency.view.number
import kotlinx.android.synthetic.main.item_currency.view.priceCoin

const val ID = "id"
const val NAME = "name"

class CryptoPanelListAdapter(private val onClickListener: (Int, String) -> Unit) :
    ListAdapter<CoinUiModel, CryptoPanelListAdapter.CoinViewHolder>(CoinDiffUtil()) {

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

        fun bindTo(coinUiModel: CoinUiModel) {
            with(itemView) {
                number.text = coinUiModel.marketCapRank.toInt().toString()
                dayChange.text = buildString {
                    append("%.2f")
                }.format(coinUiModel.dayChange)
                dayChange.setTextColor(setColor(coinUiModel.dayChange))
                nameCoin.text = coinUiModel.name
                priceCoin.text = coinUiModel.price.toFormat()

                Picasso.get()
                    .load(coinUiModel.imageUrl)
                    .resize(100, 100)
                    .into(imageView)

                if (topList.contains(coinUiModel.id)) {
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
        holder.bindTo(getItem(position))
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