package com.example.cryptopanel.ui.tradeHelper

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptopanel.databinding.FragmentTradeHelperBinding
import com.example.cryptopanel.utils.BindingFragment
import com.example.cryptopanel.ui.mainScreen.TOPLIST
import com.example.cryptopanel.ui.coinDetails.toFormat
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.ui.mainScreen.CryptoPanelViewModel
import kotlinx.android.synthetic.main.fragment_trade_helper.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.core.qualifier.named

const val FIRST_PRICE = 0.0
const val START_SYMBOL = "$"
const val START_CURRENCY = 1.0

class FragmentTradeHelper :
    BindingFragment<FragmentTradeHelperBinding>(FragmentTradeHelperBinding::class) {
    private val viewModel: CryptoPanelViewModel by activityViewModel()
    private val prefs: SharedPreferences by inject(named("topListPrefs"))
    var enteredNumber = FIRST_PRICE
    var currency = START_CURRENCY

    private val adapter = TradeHelperAdapter { id, quantity, convert ->
        what_coin.text = id
        enteredNumber = quantity
        currency = convert
        editTextNumber.setText(quantity.toFormat())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data: List<Coin> = viewModel.getData()

        val filteredData = viewModel.filterIt(
            prefs.getStringSet(TOPLIST, mutableSetOf())?.toList()!!,
            data
        )


        compare_rw.layoutManager = LinearLayoutManager(context)
        compare_rw.adapter = adapter
        compare_rw.setHasFixedSize(true)

        what_coin.text = START_SYMBOL


        convert_btn.setOnClickListener {
            enteredNumber = editTextNumber.text.toString().toDouble() * currency
            adapter.setFilteredList(filteredData, enteredNumber)
        }



        adapter.setFilteredList(filteredData, FIRST_PRICE)

    }
}