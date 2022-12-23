package com.example.cryptopanel.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cryptopanel.R
import com.example.cryptopanel.adapters.doubleToString
import com.example.cryptopanel.adapters.setColor
import com.example.cryptopanel.databinding.FragmentCoinDetailsBinding
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.viewModels.CryptoPanelViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_coin_details.*

class FragmentCoinDetails : Fragment(R.layout.fragment_coin_details) {
    //lateinit var только в тестах или инжекте (если по какой-то причине нелзя заинжектить через констуктор)
    private lateinit var binding: FragmentCoinDetailsBinding
    private val viewModel: CryptoPanelViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCoinDetailsBinding.bind(view)
        //захардкоженное ID
        val position = arguments?.getInt("id")!!

        viewModel.coinsList.observe(viewLifecycleOwner) {
            updateUI(it[position])
        }
    }

    private fun updateUI(coin: Coin) {
        binding.apply {
            idCoin.text = coin.id
            price.text = coin.current_price.toString()
            dayChanges.text = doubleToString(coin.price_change_24h)
            dayChanges.setTextColor(setColor(coin.price_change_24h))
            rank.text = getString(R.string.rank, coin.market_cap_rank.toInt())
            marketCap.text = getString(R.string.market_cap, coin.market_cap.toInt())
            circulation.text = getString(R.string.total_supply, coin.total_supply.toInt())
            low_24.text = getString(R.string.low_24, coin.low_24h.toInt())
            high_24.text = getString(R.string.high_24, coin.high_24h.toInt())

            val yData = ArrayList<Entry>()
            val arrayListOfDouble = coin.sparkline_in_7d.price
            //мэджик намберс
            for(index in arrayListOfDouble.indices){
                if(index % 24 == 0)
                yData.add(Entry(index.toFloat(), arrayListOfDouble[index].toFloat()))
            }

            val set = LineDataSet(yData, "1")

            set.setDrawCircles(false)
            chart.description.text = ""
            chart.xAxis.setDrawAxisLine(false)

            set.setDrawIcons(false)
            set.setDrawHorizontalHighlightIndicator(false)
            set.setDrawVerticalHighlightIndicator(false)
            set.setDrawValues(false)


            set.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            set.setDrawFilled(true)

            //в ресурсы
            set.label = "last 7 days"

            val dataSet = ArrayList<ILineDataSet>()
            dataSet.add(set)

            chart.data = LineData(dataSet)

            //мэджик намберс
            Picasso.get()
                .load(coin.image)
                .resize(200, 200)
                .into(imageView2)
        }
    }
}