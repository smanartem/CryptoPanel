package com.example.cryptopanel.ui.coinDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cryptopanel.R
import com.example.cryptopanel.databinding.FragmentCoinDetailsBinding
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.ui.mainScreen.CryptoPanelViewModel
import com.example.cryptopanel.ui.mainScreen.ID
import com.example.cryptopanel.utils.setColor
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_coin_details.chart
import kotlinx.android.synthetic.main.fragment_coin_details.circulation
import kotlinx.android.synthetic.main.fragment_coin_details.dayChanges
import kotlinx.android.synthetic.main.fragment_coin_details.high_24
import kotlinx.android.synthetic.main.fragment_coin_details.imageView2
import kotlinx.android.synthetic.main.fragment_coin_details.low_24
import kotlinx.android.synthetic.main.fragment_coin_details.marketCap
import kotlinx.android.synthetic.main.fragment_coin_details.price
import kotlinx.android.synthetic.main.fragment_coin_details.rank
import org.koin.androidx.viewmodel.ext.android.activityViewModel

fun Double.toFormat() = if (this < 1) "%.6f".format(this) else "%.2f".format(this)

const val HOURS_DAY = 24
const val AXIS_TEXT_SIZE = 15f

class FragmentCoinDetails : Fragment() {

    private lateinit var binding: FragmentCoinDetailsBinding

    private val viewModel: CryptoPanelViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoinDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(ID)!!

        viewModel.coinsListLive.observe(viewLifecycleOwner) {
            updateUI(it[position])
        }
    }

    private fun updateUI(coin: Coin) {
        price.text = coin.current_price.toString()
        dayChanges.text = coin.price_change_24h.toFormat()
        dayChanges.setTextColor(setColor(coin.price_change_24h))
        rank.text = getString(R.string.rank, coin.market_cap_rank.toInt())
        marketCap.text = getString(R.string.market_cap, coin.market_cap.toInt())
        circulation.text = getString(R.string.total_supply, coin.total_supply.toInt())
        low_24.text = getString(R.string.low_24, coin.low_24h.toInt())
        high_24.text = getString(R.string.high_24, coin.high_24h.toInt())

        setupLineChart(coin.sparkline_in_7d.price)

        Picasso.get()
            .load(coin.image)
            .resize(200, 200)
            .into(imageView2)
    }

    private fun setupLineChart(data: ArrayList<Double>): Unit {
        val yData = ArrayList<Entry>()

        for (index in data.indices) {
            if (index % HOURS_DAY == 0)
                yData.add(Entry(index.toFloat(), data[index].toFloat()))
        }

        val set = LineDataSet(yData, "")

        with(chart) {
            setDrawGridBackground(false)
            description = null
            legend.isEnabled = false

            xAxis.apply {
                setDrawAxisLine(false)
                setDrawLabels(false)
                setDrawGridLines(false)
            }

            axisLeft.apply {
                setDrawGridLines(true)
                setDrawLabels(true)
                textColor = ContextCompat.getColor(requireContext(), R.color.greenLite)
                textSize = AXIS_TEXT_SIZE
            }

            axisRight.apply {
                isEnabled = false
            }
        }

        with(set) {
            setDrawCircles(false)
            setDrawValues(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawFilled(true)
            color = ContextCompat.getColor(requireContext(), R.color.green)
            fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.bg_spark_line)
        }

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(set)
        chart.data = LineData(dataSet)
    }
}