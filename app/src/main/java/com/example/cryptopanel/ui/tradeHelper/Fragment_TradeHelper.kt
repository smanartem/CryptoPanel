package com.example.cryptopanel.ui.tradeHelper

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptopanel.data.data.model.CoinDataModel
import com.example.cryptopanel.databinding.FragmentTradeHelperBinding
import com.example.cryptopanel.domain.mapToListUiModels
import com.example.cryptopanel.ui.mainScreen.CryptoPanelViewModel
import com.example.cryptopanel.ui.mainScreen.TOPLIST
import com.example.cryptopanel.utils.toFormat
import kotlinx.android.synthetic.main.fragment_trade_helper.compare_rw
import kotlinx.android.synthetic.main.fragment_trade_helper.convert_btn
import kotlinx.android.synthetic.main.fragment_trade_helper.editTextNumber
import kotlinx.android.synthetic.main.fragment_trade_helper.what_coin
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.core.qualifier.named

const val FIRST_PRICE = 0.0
const val START_SYMBOL = "$"
const val START_CURRENCY = 1.0

class FragmentTradeHelper : Fragment() {

    private var _binding: FragmentTradeHelperBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: CryptoPanelViewModel by activityViewModel()
    private val prefs: SharedPreferences by inject(named("topListPrefs"))
    private var enteredNumber = FIRST_PRICE
    private var currency = START_CURRENCY

    private val adapter = TradeHelperAdapter { id, quantity, convert ->
        what_coin.text = id
        enteredNumber = quantity
        currency = convert
        editTextNumber.setText(quantity.toFormat())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTradeHelperBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data: List<CoinDataModel> = viewModel.getData()

        val filteredData = viewModel.filterIt(
            prefs.getStringSet(TOPLIST, mutableSetOf())?.toList()!!,
            data
        ).mapToListUiModels()


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