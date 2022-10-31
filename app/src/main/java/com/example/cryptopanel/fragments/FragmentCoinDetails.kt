package com.example.cryptopanel.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cryptopanel.R
import com.example.cryptopanel.adapters.SparklineAdapter
import com.example.cryptopanel.adapters.setColor
import com.example.cryptopanel.databinding.FragmentCoinDetailsBinding
import com.example.cryptopanel.model.Coin
import com.example.cryptopanel.viewModels.CryptoPanelViewModel
import com.robinhood.spark.SparkAdapter
import com.squareup.picasso.Picasso

class FragmentCoinDetails : Fragment(R.layout.fragment_coin_details) {
    private lateinit var binding: FragmentCoinDetailsBinding
    private val viewModel: CryptoPanelViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCoinDetailsBinding.bind(view)
        val position = arguments?.getInt("id")!!.toInt()

        viewModel.coinsList.observe(viewLifecycleOwner) {
            val coin = it[position]
            updateUI(coin)
        }
    }

    private fun updateUI(coin: Coin) {
        val adapter: SparkAdapter = SparklineAdapter(coin.sparkline_in_7d.price)
        binding.apply {
            sparkView.lineColor = setColor(coin.price_change_24h)
            sparkView.adapter = adapter
            idCoin.text = coin.id
            price.text = coin.current_price.toString()
            dayChanges.text = coin.price_change_24h.toString()
            dayChanges.setTextColor(setColor(coin.price_change_24h))
            Picasso.get()
                .load(coin.image)
                .resize(100, 100)
                .into(imageView2)
        }
    }
}