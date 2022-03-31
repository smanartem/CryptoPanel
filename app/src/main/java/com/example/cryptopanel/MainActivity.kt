package com.example.cryptopanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.*
import com.example.cryptopanel.Model.Coin
import com.example.cryptopanel.Retrofit.RetrofitClient
import com.example.cryptopanel.Retrofit.RetrofitServieces
import retrofit2.*

private const val TAG = "getting DATA"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview: RecyclerView = findViewById(R.id.recycler_view)
        recyclerview.layoutManager = LinearLayoutManager(this)

// создаем объект ретрофит который содержит url и обрабатывает json
        val retrofitService: RetrofitServieces =
            RetrofitClient.getClient().create(RetrofitServieces::class.java)
//    получаем массив Монет из респонс, передаем в массив coins и помещаем в адаптер
        retrofitService.getCoins().enqueue(object : Callback<List<Coin>> {
            override fun onResponse(call: Call<List<Coin>>, response: Response<List<Coin>>) {
                recyclerview.adapter = CryptoPanelAdapter(applicationContext, response.body()!!)
            }

            override fun onFailure(call: Call<List<Coin>>, t: Throwable) {
                Log.e(TAG, "ERRROOORRRR")
            }
        })
    }
}
