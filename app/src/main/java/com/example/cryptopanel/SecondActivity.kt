package com.example.cryptopanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.cryptopanel.model.Coin
import com.robinhood.spark.SparkAdapter
import com.robinhood.spark.SparkView

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
        val idS: TextView = findViewById(R.id.idS)
        val priceS: TextView = findViewById(R.id.priceS)
        val marketCup: TextView = findViewById(R.id.marketCupS)
        val change: TextView = findViewById(R.id.priceChangeS)

        nameS.text = coin?.name
        idS.text = coin?.id
        priceS.text = "%.3f".format(coin?.current_price)
        marketCup.text = "Market Cup \n ${"%.3f".format(coin?.market_cap)}"
        change.text = "Day change \n ${"%.3f".format(coin?.price_change_24h)}"
    }
}