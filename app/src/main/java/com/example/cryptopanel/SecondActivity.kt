package com.example.cryptopanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.cryptopanel.Model.Coin

class SecondActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val coin = intent.getSerializableExtra("c") as? Coin

        val nameS: TextView = findViewById(R.id.nameS)
        val idS: TextView = findViewById(R.id.idS)
        val priceS: TextView = findViewById(R.id.priceS)
        val marketCup: TextView = findViewById(R.id.marketCupS)
        val change: TextView = findViewById(R.id.priceChangeS)

        nameS.text = coin?.name
        idS.text = coin?.id
        priceS.text = coin?.current_price.toString()
        marketCup.text = "Market Cup \n ${coin?.market_cap.toString()}"
        change.text = "Day change \n ${ coin?.price_change_24h.toString() }"
    }
}