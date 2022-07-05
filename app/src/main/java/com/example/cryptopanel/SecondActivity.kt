package com.example.cryptopanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.cryptopanel.model.Coin
import com.robinhood.spark.SparkAdapter
import com.robinhood.spark.SparkView
import com.squareup.picasso.Picasso

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        setSupportActionBar(findViewById(R.id.second_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val coin = intent.getSerializableExtra("key") as? Coin

        val sparkView: SparkView = findViewById(R.id.spark)
        val adapter: SparkAdapter = SparklineAdapter(coin!!.sparkline_in_7d.price)
        sparkView.lineColor = setColor(coin!!.price_change_24h)
        sparkView.adapter = adapter

        val nameS: TextView = findViewById(R.id.nameS)
        val priceS: TextView = findViewById(R.id.priceS)
        val change: TextView = findViewById(R.id.priceChangeS)
        val imageViewS: ImageView = findViewById(R.id.imageViewS)

        nameS.text = coin.name
        priceS.text = buildString {
            append("%.2f")
        }.format(coin.current_price)
        change.text = buildString {
            append("%.2f")
        }.format(coin.price_change_24h)
        change.setTextColor(setColor(coin.price_change_24h))
        Picasso.get()
            .load(coin.image)
            .resize(100, 100)
            .into(imageViewS)
    }
}