package com.example.cryptopanel.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckedTextView
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

//Почитай про ListAdapter
class CryptoPanelAdapter : RecyclerView.Adapter<CryptoPanelAdapter.MyViewHolder>() {

    private val differ = AsyncListDiffer(this, MyDiffUtil())
    lateinit var topList: MutableSet<String>

    //отдельный файл
    inner class MyViewHolder(binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //эту логику лучше делать в самописном методе вьюхолдера bind() а туту просто дёргать его,
        //это делается чтобы onBindViewHolder не выростал до милиарда строк при нескольких итемах
        with(holder.itemView) {
            with(differ.currentList[position]) {
                number.text = buildString {
                    append("#")
                    append(position + 1)
                }
                dayChange.text = buildString {
                    append("%.2f")
                }.format(this.price_change_percentage_24h)
                dayChange.setTextColor(setColor(this.price_change_percentage_24h))
                nameCoin.text = this.name
                priceCoin.text = doubleToString(this.current_price)

                setOnClickListener { view ->
                    view.findNavController().navigate(
                        R.id.action_fragmentMain_to_fragmentCoinDetails,
                        bundleOf("id" to position, "name" to this.name)
                    )
                }

                if (topList.contains(this.id)) {
                    checkIsTrue(check)
                } else {
                    checkIsFalse(check)
                }

                check.setOnClickListener {
                    checkOnClickListener(it.check, topList, this.id)
                }

                Picasso.get()
                    .load(this.image)
                    .resize(100, 100)
                    .into(imageView)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun setCoinsList(newCoins: List<Coin>) {
        differ.submitList(newCoins)
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