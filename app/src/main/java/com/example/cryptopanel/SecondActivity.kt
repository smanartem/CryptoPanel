package com.example.cryptopanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cryptopanel.adapters.SparklineAdapter
import com.example.cryptopanel.adapters.setColor
import com.example.cryptopanel.databinding.ActivitySecondBinding
import com.example.cryptopanel.model.Coin
import com.robinhood.spark.SparkAdapter
import com.squareup.picasso.Picasso

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.secondToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val coin = intent.getSerializableExtra("coin") as? Coin

        val adapter: SparkAdapter = SparklineAdapter(coin!!.sparkline_in_7d.price)
        binding.spark.lineColor = setColor(coin.price_change_24h)
        binding.spark.adapter = adapter


        binding.nameS.text = coin.name
        binding.priceS.text = buildString {
            append("%.2f")
        }.format(coin.current_price)
        binding.priceChangeS.text = buildString {
            append("%.2f")
        }.format(coin.price_change_24h)
        binding.priceChangeS.setTextColor(setColor(coin.price_change_24h))
        Picasso.get()
            .load(coin.image)
            .resize(100, 100)
            .into(binding.imageViewS)
    }
}


